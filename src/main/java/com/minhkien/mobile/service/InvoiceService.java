package com.minhkien.mobile.service;

import com.minhkien.mobile.dto.request.InvoiceRequest;
import com.minhkien.mobile.dto.response.InvoiceResponse;
import com.minhkien.mobile.entity.*;
import com.minhkien.mobile.enums.DiscountType;
import com.minhkien.mobile.mapper.InvoiceMapper;
import com.minhkien.mobile.responsitory.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InvoiceService {

    UserRepository userRepo;
    ShowtimeRepository showtimeRepo;
    SeatTypeRepository seatTypeRepo;
    FoodItemRepository foodItemRepo;
    VoucherRepository voucherRepo;

    InvoiceRepository invoiceRepo;
    InvoiceDetailRepository invoiceDetailRepo;
    FoodInvoiceRepository foodInvoiceRepo;

    InvoiceMapper mapper;

    public List<InvoiceResponse> getHistoryByUser(String userId) {
        // 1. Lấy list hóa đơn từ DB
        List<Invoice> invoices = invoiceRepo.findByUser_MaUserOrderByNgayTaoDesc(userId);

        // 2. Convert sang Response
        return invoices.stream().map(this::mapToInvoiceResponse).collect(Collectors.toList());
    }

    private InvoiceResponse mapToInvoiceResponse(Invoice invoice) {

        // 1. Map List Ghế (Lấy từ giaLichSu)
        List<InvoiceResponse.GheResponse> gheList = invoice.getChiTietList().stream()
                .map(ct -> InvoiceResponse.GheResponse.builder()
                        .maSeatType(ct.getGhe().getMaSeatType())
                        .tenLoaiGhe(ct.getGhe().getTenSeatType())
                        .gia(ct.getGiaLichSu()) // <--- Lấy giá lịch sử, KHÔNG lấy giá hiện tại
                        .build())
                .collect(Collectors.toList());

        // 2. Map List Đồ ăn (Lấy từ giaLichSu)
        List<InvoiceResponse.DoAnResponse> doAnList = invoice.getDoAnList().stream()
                .map(fi -> InvoiceResponse.DoAnResponse.builder()
                        .foodId(fi.getDoAn().getMaFoodItem())
                        .tenDoAn(fi.getDoAn().getTenFoodItem())
                        .soLuong(fi.getSoLuong())
                        .gia(fi.getGiaLichSu()) // <--- Lấy giá lịch sử
                        .thanhTien(fi.getGiaLichSu() * fi.getSoLuong())
                        .build())
                .collect(Collectors.toList());

        // 3. Trả về Response (Lấy thẳng từ các cột đã lưu trong Invoice)
        return InvoiceResponse.builder()
                .maHoaDon(invoice.getMaHoaDon())
                .userName(invoice.getUser().getHoTen())
                .tenPhim(invoice.getShowtime().getFilm().getTenPhim())
                .ngayTao(invoice.getNgayTao())
                .tgBatDau(invoice.getShowtime().getTgBatDau())
                .tenRap(invoice.getShowtime().getCinema().getTenRap())
                .diaChiRap(invoice.getShowtime().getCinema().getDiaDiem())
                .tenPhong(invoice.getShowtime().getRoom().getTenPhong())
                .voucher(invoice.getVoucher() != null ? invoice.getVoucher().getMaGiamGia() : null)
                .tongTienTruocGiam(invoice.getTongTienGoc())
                .soTienGiam(invoice.getSoTienGiam())
                .tongTienSauGiam(invoice.getTongTien())
                .gheList(gheList)
                .doAnList(doAnList)
                .build();
    }

    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest req) {

        User user = userRepo.findById(req.getMaUser())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        Showtime showtime = showtimeRepo.findById(req.getMaSuatChieu())
                .orElseThrow(() -> new RuntimeException("Suất chiếu không tồn tại"));

        Voucher voucher = null;
        if (req.getVoucherId() != null) {
            voucher = voucherRepo.findById(req.getVoucherId())
                    .orElseThrow(() -> new RuntimeException("Voucher không tồn tại"));
        }

        Invoice invoice = new Invoice();
        invoice.setUser(user);
        invoice.setShowtime(showtime);
        invoice.setVoucher(voucher);
        invoice.setNgayTao(LocalDateTime.now());
        invoice.setTrangThai(true);
        invoiceRepo.save(invoice);

        double tongTienGhe = 0.0;
        double tongTienDoAn = 0.0;

        // ======= GHẾ =======
        List<InvoiceResponse.GheResponse> gheResponseList = new ArrayList<>();

        for (InvoiceRequest.SeatRequest s : req.getGheList()) {

            SeatType seat = seatTypeRepo.findById(s.getMaSeatType())
                    .orElseThrow(() -> new RuntimeException("SeatType không tồn tại"));

            InvoiceDetail ct = new InvoiceDetail();
            ct.setHoaDon(invoice);
            ct.setGhe(seat);
            ct.setGiaLichSu(seat.getGia());
            invoiceDetailRepo.save(ct);

            tongTienGhe += seat.getGia();

            gheResponseList.add(
                    InvoiceResponse.GheResponse.builder()
                            .maSeatType(seat.getMaSeatType())
                            //.tenLoaiGhe(seat.getTenLoai())
                            .gia(seat.getGia())
                            .build()
            );
        }

        // ======= ĐỒ ĂN =======
        List<InvoiceResponse.DoAnResponse> doAnResponseList = new ArrayList<>();

        for (InvoiceRequest.FoodRequest f : req.getDoAnList()) {

            FoodItem food = foodItemRepo.findById(f.getFoodId())
                    .orElseThrow(() -> new RuntimeException("Food không tồn tại"));

            FoodInvoice fi = new FoodInvoice();
            fi.setHoaDon(invoice);
            fi.setDoAn(food);
            fi.setSoLuong(f.getSoLuong());
            fi.setGiaLichSu(food.getGia());
            foodInvoiceRepo.save(fi);

            tongTienDoAn += food.getGia() * f.getSoLuong();

            doAnResponseList.add(
                    InvoiceResponse.DoAnResponse.builder()
                            .foodId(food.getMaFoodItem())
                            .tenDoAn(food.getTenFoodItem())
                            .soLuong(f.getSoLuong())
                            .gia(food.getGia())
                            .thanhTien(food.getGia() * f.getSoLuong())
                            .build()
            );
        }

        double tongTienTruocGiam = tongTienGhe + tongTienDoAn;
        double soTienGiam = tinhGiamGia(voucher, tongTienTruocGiam);
        double tongTienSauGiam = tongTienTruocGiam - soTienGiam;

        invoice.setTongTien(tongTienSauGiam);
        invoice.setTongTienGoc(tongTienTruocGiam);
        invoice.setSoTienGiam(soTienGiam);
        invoiceRepo.save(invoice);

        if (voucher != null) {
            int currentQty = voucher.getSoLuong();
            if (currentQty <= 0) {
                throw new RuntimeException("Voucher đã hết lượt sử dụng");
            }
            voucher.setSoLuong(currentQty - 1);
            voucherRepo.save(voucher);
        }

        return InvoiceResponse.builder()
                .maHoaDon(invoice.getMaHoaDon())
                .userName(user.getHoTen())
                .tenPhim(showtime.getFilm().getTenPhim())
                .ngayTao(invoice.getNgayTao())
                .tgBatDau(showtime.getTgBatDau())
                .tenRap(showtime.getCinema().getTenRap())
                .diaChiRap(showtime.getCinema().getDiaDiem())
                .tenPhong(showtime.getRoom().getTenPhong())
                .voucher(voucher != null ? voucher.getMaGiamGia() : null)
                .tongTienTruocGiam(tongTienTruocGiam)
                .soTienGiam(soTienGiam)
                .tongTienSauGiam(tongTienSauGiam)
                .gheList(gheResponseList)
                .doAnList(doAnResponseList)
                .build();
    }

    private double tinhGiamGia(Voucher voucher, double tong) {
        if (voucher == null) return 0;

        if (voucher.getLoaiGiamGia() == DiscountType.PERCENTAGE) {
            return tong * (voucher.getGiaTriGiam() / 100);
        }

        if (voucher.getLoaiGiamGia() == DiscountType.AMOUNT) {
            return voucher.getGiaTriGiam();
        }

        return 0;
    }
}
