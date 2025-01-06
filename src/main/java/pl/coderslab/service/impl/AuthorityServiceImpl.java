//package pl.coderslab.service.impl;
//
//import org.springframework.stereotype.Service;
//import pl.coderslab.entity.Authority;
//import pl.coderslab.entity.User;
//import pl.coderslab.repository.AuthorityRepository;
//import pl.coderslab.repository.UserRepository;
//import pl.coderslab.service.AuthorityService;
//import pl.coderslab.service.UserService;
//
//@Service
//public class AuthorityServiceImpl implements AuthorityService {
//    private final AuthorityRepository authorityRepository;
//
//    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
//        this.authorityRepository = authorityRepository;
//    }
//
//    @Override
//    public Authority findByAuthority(String authority) {
//        return authorityRepository.findByAuthority(authority);
//    }
//}
