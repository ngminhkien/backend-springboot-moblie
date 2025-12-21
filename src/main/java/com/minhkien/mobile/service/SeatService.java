package com.minhkien.mobile.service;

import com.minhkien.mobile.dto.response.SeatConfigResponse;
import com.minhkien.mobile.entity.SeatType;
import com.minhkien.mobile.responsitory.InvoiceDetailRepository;
import com.minhkien.mobile.responsitory.SeatTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SeatService {

    SeatTypeRepository seatTypeRepository;
    InvoiceDetailRepository invoiceDetailRepository;

    public List<SeatConfigResponse> getAllSeatConfigs() {
        List<SeatType> list = seatTypeRepository.findAll();

        return list.stream().map(s -> SeatConfigResponse.builder()
                        .maSeatType(s.getMaSeatType())
                        .tenSeatType(s.getTenSeatType())
                        .gia(s.getGia())                 // Mapping giá
                        .build())
                .collect(Collectors.toList());
    }

    public List<String> getBookedSeats(Long maSuatChieu) {
        return invoiceDetailRepository.findBookedSeatCodes(maSuatChieu);
    }
}
