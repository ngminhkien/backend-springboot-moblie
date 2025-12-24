package com.minhkien.mobile.dto.response.RevenueReponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopMovieRevenueResponse {
    String movieId;
    String movieName;
    long tickets;
    long revenue;
}

