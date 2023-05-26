package com.example.demo.service;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ClimbingCommentService {

}
