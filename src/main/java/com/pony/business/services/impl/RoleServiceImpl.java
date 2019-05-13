package com.pony.business.services.impl;

import com.pony.business.services.RoleService;
import com.pony.data.repositories.RoleRepository;
import com.pony.entities.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository _roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this._roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return _roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findById(Long roleId) {
        return _roleRepository.findById(roleId).orElse(null);
    }

    @Override
    public Role findByName(String roleName) {
        return _roleRepository.findByName(roleName); 
    }

    @Override
    public Role insert(Role role) {
        return _roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        return _roleRepository.save(role);
    }

    @Override
    @Transactional
    public void delete(Long roleId) {
        _roleRepository.deleteById(roleId);
//                .delete(roleId);
    }

    public Role addRole(Role role) {
        if (_roleRepository.findByName(role.getName()) == null) {
            return _roleRepository.save(role);
        }

        return null;
    }

    public boolean deleteRole(Role role) {

        Role targetRole = _roleRepository.findByName(role.getName());

        if (targetRole != null) {
            _roleRepository.delete(targetRole);

            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public void insetDefaultRoles() {
        String[] names = {"USER", "WRITER", "MODERATOR", "DEVELOPER", "ADMIN", "SUPERADMIN"};
        
        List<Role> existingRoles = _roleRepository.findAll();
        List<Role> roles = new ArrayList<Role>();

        for (String name : names)
        {
            if (existingRoles.stream().noneMatch(x -> x.getName().equals(name))) {
//            if (existingRoles.stream().anyMatch(x -> x.getName().equals(name)) == false) {
                roles.add(new Role(name));
            }
        }

        _roleRepository.saveAll(roles);
//                .save(roles);
    }
}