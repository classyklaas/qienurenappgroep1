package app.qienuren.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BerichtService {
    @Autowired BerichtRepository berichtrepository;
}
