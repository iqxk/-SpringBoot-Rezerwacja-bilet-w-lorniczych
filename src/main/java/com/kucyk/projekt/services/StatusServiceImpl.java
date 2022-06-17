package com.kucyk.projekt.services;

import com.kucyk.projekt.models.Status;
import com.kucyk.projekt.repositories.StatusRepository;
import com.kucyk.projekt.services.interfaces.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("statusService")
public class StatusServiceImpl implements StatusService
{
    @Autowired
    StatusRepository statusRepository;

    public Status getById(long id)
    {
        return statusRepository.findById(id).get();
    }

    public List<Status> getAllStatuses()
    {
        return statusRepository.findAll();
    }
}
