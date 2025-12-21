package com.minhkien.mobile.mapper;

import com.minhkien.mobile.dto.request.voucher.VoucherCreateRequest;
import com.minhkien.mobile.dto.request.voucher.VoucherUpdateRequest;
import com.minhkien.mobile.dto.response.VoucherResponse;
import com.minhkien.mobile.entity.Voucher;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VoucherMapper {

    // 1. CreateRequest -> Entity
    // MapString (String) sang Enum (DiscountType) tự động nếu trùng tên
    Voucher toEntity(VoucherCreateRequest request);

    // 2. Entity -> Response
    VoucherResponse toResponse(Voucher voucher);

    // 3. UpdateRequest -> Entity (Chỉ update các trường có dữ liệu)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateVoucherFromRequest(VoucherUpdateRequest request, @MappingTarget Voucher entity);
}
