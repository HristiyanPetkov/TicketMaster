package com.example.ticketmasterapi.mappers;

import com.example.ticketmasterapi.dto.LookupTableResource;
import com.example.ticketmasterapi.models.LookupTableEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LookupTableMapper {
    LookupTableMapper LOOKUP_TABLE_MAPPER = Mappers.getMapper(LookupTableMapper.class);

    public LookupTableResource toLookupTableResource(LookupTableEntity lookupTableEntity);

    public LookupTableEntity fromLookupTableResource(LookupTableResource lookupTableResource);

    public List<LookupTableResource> toLookupTableResources(List<LookupTableEntity> lookupTableEntityList);

    public List<LookupTableEntity> fromLookupTableResources(List<LookupTableResource> lookupTableResourceList);

}
