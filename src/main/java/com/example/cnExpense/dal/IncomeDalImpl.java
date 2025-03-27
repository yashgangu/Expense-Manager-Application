package com.example.cnExpense.dal;

import com.example.cnExpense.entity.Income;
import com.example.cnExpense.entity.User;
import com.example.cnExpense.service.UserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class IncomeDalImpl implements IncomeDal {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserService userService;

    @Override
    public Income getIncomeById(Long incomeId) {
    	Session session=entityManager.unwrap(Session.class);
    	Income income=session.get(Income.class, incomeId);
    	return income;
    }

    @Override
    public Income saveIncome(User user, Income newIncome) {
    	Session session=entityManager.unwrap(Session.class);
//    	newIncome.setUser(user);
//    	return newIncome;
    	 // Save new income
        session.save(newIncome);

        // Retrieve and associate the user with the income
        User existingUser = userService.getUserById(user.getId());
        existingUser.getIncomes().add(newIncome);
        newIncome.setUser(existingUser);

        session.saveOrUpdate(newIncome);
        session.saveOrUpdate(existingUser);

        return newIncome;
    }

    @Override
    public Income updateIncome(Long incomeId, Income income) {
    	Session session=entityManager.unwrap(Session.class);
    	Income existingIncome=session.get(Income.class, incomeId);
    	if(existingIncome !=null) {
    		existingIncome.setAmount(income.getAmount());
    		existingIncome.setDate(income.getDate());
    		existingIncome.setDescription(income.getDescription());
    		existingIncome.setIncomeType(income.getIncomeType());
    		session.update(existingIncome);
    	}
    	return existingIncome;
        
    }

}


