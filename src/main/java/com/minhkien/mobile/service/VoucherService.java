package com.minhkien.mobile.service;

import com.minhkien.mobile.dto.request.voucher.VoucherCreateRequest;
import com.minhkien.mobile.dto.request.voucher.VoucherUpdateRequest;
import com.minhkien.mobile.dto.response.VoucherResponse;
import com.minhkien.mobile.entity.Voucher;
import com.minhkien.mobile.mapper.VoucherMapper;
import com.minhkien.mobile.responsitory.VoucherRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //tạo constructor với những field final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)//mặc định là private final
@Slf4j
public class VoucherService {

    VoucherRepository voucherRepository;
    VoucherMapper voucherMapper;

    // Lấy tất cả
    public List<VoucherResponse> getAllVouchers() {
        return voucherRepository.findAll().stream()
                .map(voucherMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Tạo mới
    public VoucherResponse createVoucher(VoucherCreateRequest request) {
        if (voucherRepository.existsById(request.getMaGiamGia())) {
            throw new RuntimeException("Mã giảm giá " + request.getMaGiamGia() + " đã tồn tại!");
        }

        Voucher voucher = voucherMapper.toEntity(request);

        // Set giá trị mặc định hệ thống tự quản lý
        voucher.setNgayTao(LocalDateTime.now());
        voucher.setTrangThai(true); // Mặc định vừa tạo là Active

        Voucher savedVoucher = voucherRepository.save(voucher);
        return voucherMapper.toResponse(savedVoucher);
    }

    // Cập nhật
    public VoucherResponse updateVoucher(String maGiamGia, VoucherUpdateRequest request) {
        Voucher existingVoucher = voucherRepository.findById(maGiamGia)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy voucher!"));

        // Map data mới vào entity cũ
        voucherMapper.updateVoucherFromRequest(request, existingVoucher);

        Voucher updated = voucherRepository.save(existingVoucher);
        return voucherMapper.toResponse(updated);
    }

    // Xóa
    public void deleteVoucher(String maGiamGia) {
        if (!voucherRepository.existsById(maGiamGia)) {
            throw new RuntimeException("Voucher không tồn tại!");
        }
        voucherRepository.deleteById(maGiamGia);
    }
}
