package com.crud.serviceimpl;

import com.crud.entity.Claim;
import com.crud.repository.ClaimRepository;
import com.crud.repository.UserRepository;
import com.crud.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimImpl implements ClaimService {

    @Autowired
    private ClaimRepository claimRepo;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Claim addClaim(Claim claim) {
        Long userId = claim.getUser().getUserId();
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return claimRepo.save(claim);
    }

    @Override
    public List<Claim> getClaimsByUserId(Long userId) {
        return claimRepo.findByUserUserId(userId);
    }


}
