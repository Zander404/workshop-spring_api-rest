package com.xandy.spring_rest.dto.mapper;


import com.xandy.spring_rest.dto.PageableDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableDTO toDTO(Page page) {
        return new ModelMapper().map(page,
                PageableDTO.class);
    }
}
