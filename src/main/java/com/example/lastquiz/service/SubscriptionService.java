package com.example.lastquiz.service;

import com.example.lastquiz.entity.Subscription;
import com.example.lastquiz.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription saveSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(Integer id) {
        subscriptionRepository.deleteById(id);
    }
}
