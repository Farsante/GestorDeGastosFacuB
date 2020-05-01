package com.example.gestordegastosfacub.repositories;

import com.example.gestordegastosfacub.helpers.SessionPersistence;
import com.example.gestordegastosfacub.models.User;

public class HomeRepository {

    public User getUser(){return SessionPersistence.getUser(); }
}
