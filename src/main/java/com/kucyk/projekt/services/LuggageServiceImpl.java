package com.kucyk.projekt.services;

import com.kucyk.projekt.models.Luggage;
import com.kucyk.projekt.repositories.LuggageRepository;
import com.kucyk.projekt.services.interfaces.LuggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("luggageService")
public class LuggageServiceImpl implements LuggageService
{
    @Autowired
    LuggageRepository luggageRepository;

    @Override
    public Luggage getById(long id)
    {
        return luggageRepository.getById(id);
    }

    public Luggage saveLuggage(Luggage luggage)
    {
        return luggageRepository.save(luggage);
    }
}
