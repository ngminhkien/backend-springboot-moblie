package com.minhkien.mobile.mapper;

import com.minhkien.mobile.dto.request.film.FilmCreationRequest;
import com.minhkien.mobile.dto.request.film.FilmUpdateRequest;
import com.minhkien.mobile.dto.response.FilmResponse;
import com.minhkien.mobile.entity.Film;
import com.minhkien.mobile.entity.Genre;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    @Mapping(target = "trangThai", ignore = true)
    @Mapping(target = "genres", ignore = true)
    Film toFilm(FilmCreationRequest request);

    // Hàm toFilmResponse đã đúng
    @Mapping(source = "maPhim", target = "maPhim")
    @Mapping(target = "genres",
            expression = "java(film.getGenres().stream().map(g -> g.getTenGenre()).collect(java.util.stream.Collectors.toSet()))")
    FilmResponse toFilmResponse(Film film);

    @Mapping(target = "genres", source = "genres", qualifiedByName = "mapGenresToNames")
    FilmResponse toResponse(Film film);

    // 2. Convert từ Request sang Entity (Tạo mới)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "maPhim", ignore = true)
    @Mapping(target = "trangThai", ignore = true)
    Film toEntity(FilmCreationRequest request);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "maPhim", ignore = true)
    void updateFilmFromRequest(FilmUpdateRequest request, @MappingTarget Film film);

    @Named("mapGenresToNames")
    default Set<String> mapGenresToNames(Set<Genre> genres) {
        if (genres == null) {
            return new HashSet<>();
        }
        return genres.stream()
                .map(Genre::getTenGenre)
                .collect(Collectors.toSet());
    }
}
