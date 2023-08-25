package com.example.lr7.service;

import com.example.lr7.dto.UserDto;
import com.example.lr7.entity.Role;
import com.example.lr7.entity.User;
import com.example.lr7.repository.RoleRepository;
import com.example.lr7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder){
this.roleRepository=roleRepository;
this.userRepository=userRepository;
this.passwordEncoder=passwordEncoder;
    }
@Override
    public void saveUser (UserDto userDto){
        User user=new User();
        user.setName(userDto.getFirstName()+ " "+userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role=roleRepository.findByName("ROLE_ADMIN");
        if (role==null){
            role=checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
}
@Override
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
}
@Override
    public List<UserDto>findAllUsers(){
        List<User>users=userRepository.findAll();
        return users.stream().map((user)->mapToUserDto(user)).collect(Collectors.toList());
}
private UserDto mapToUserDto(User user){
        UserDto userDto=new UserDto();
        String[] str =user.getName().split("");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
}
private Role checkRoleExist(){
        Role role=new Role();
        role.setName("Role_ADMIN");
        return roleRepository.save(role);
}
}
