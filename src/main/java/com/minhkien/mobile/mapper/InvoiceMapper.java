package com.minhkien.mobile.mapper;

import com.minhkien.mobile.dto.response.InvoiceResponse;
import com.minhkien.mobile.entity.FoodInvoice;
import com.minhkien.mobile.entity.SeatType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    InvoiceResponse.GheResponse toGheResponse(SeatType seat);

//    @Mapping(target = "foodId", source = "maFoodItem")
//    @Mapping(target = "tenDoAn", source = "tenFoodItem")
//    @Mapping(target = "gia", source = "gia")
//    @Mapping(target = "thanhTien", expression = "java(food.getDoAn().getGia() * food.getSoLuong())")
//    InvoiceResponse.DoAnResponse toDoAnResponse(FoodInvoice food);
}
