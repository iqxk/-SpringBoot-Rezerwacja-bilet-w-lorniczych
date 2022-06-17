package com.kucyk.projekt.services;

import com.kucyk.projekt.repositories.RoleRepository;
import com.kucyk.projekt.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService
{
    @Autowired
    RoleRepository roleRepository;
}
