package com.example.cnExpense.dal;

import com.example.cnExpense.entity.Expense;
import com.example.cnExpense.entity.Income;
import com.example.cnExpense.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDalImpl implements UserDal {

	@Autowired
	EntityManager entityManager;

	@Override
	public User getUserById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, id);
		return user;
	}

	@Override
	public User saveUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		session.save(user);
		return user;

	}

	@Override
	public User updateUser(Long userId, User user) {
		Session session = entityManager.unwrap(Session.class);
		User existingUser = session.get(User.class, userId);
		if (existingUser != null) {
			existingUser.setUsername(user.getUsername());
			existingUser.setNickname(user.getNickname());
			existingUser.setEmail(user.getEmail());
			existingUser.setAddress(user.getAddress());
			existingUser.setBudget(user.getBudget());

			session.update(existingUser);

		}
		return existingUser;
	}

	@Override
	public User setBudget(Long userId, double budget) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, userId);
		if (user != null) {
			user.setBudget(budget);
			session.update(user);
		}
		return user;
	}

	@Override
	public double getTotalExpense(Long userId) {
//		Session session = entityManager.unwrap(Session.class);
//		User user = session.get(User.class, userId);
//		if (user == null || user.getExpenses() == null) {
//			return 0.0;
//		}
//		double totalExpense = 0.0;
//		for (Expense expense : user.getExpenses()) {
//			totalExpense += expense.getAmount();
//		}
//		return totalExpense;
		 User user = getUserById(userId);
	        double totalExpense = 0.0;
	        for (Expense expense : user.getExpenses()) {
	            totalExpense += expense.getAmount();
	        }
	        return totalExpense;
	}

	@Override
	public double getQuotation(Long userId) {
//		Session session = entityManager.unwrap(Session.class);
//		User user = session.get(User.class, userId);
//
//		if (user != null) {
//			double totalIncome = getTotalIncome(userId);
//			double totalExpense = getTotalExpense(userId);
//			return totalIncome - totalExpense;
//		}
//
//		return 0.0;
		  User user = getUserById(userId);
	        double totalIncome = 0.0;
	        for (Income income : user.getIncomes()) {
	            totalIncome += income.getAmount();
	        }
	        double totalExpense = getTotalExpense(userId);
	        return totalIncome - totalExpense;
	}

	@Override
	public double getAvgExpenseData(Long userId) {

//		Session session = entityManager.unwrap(Session.class);
//		User user = session.get(User.class, userId);
//
//		if (user != null && user.getExpenses() != null && !user.getExpenses().isEmpty()) {
//			double totalExpense = 0.0;
//			int count = 0;
//
//			for (Expense expense : user.getExpenses()) {
//				totalExpense += expense.getAmount();
//				count++;
//			}
//
//			return totalExpense / count;
//		}
//
//		return 0.0;
		
		  User user = getUserById(userId);
	        List<Expense> expenses = user.getExpenses();
	        if (expenses.isEmpty()) {
	            return 0.0;
	        }
	        double totalExpense = 0.0;
	        for (Expense expense : expenses) {
	            totalExpense += expense.getAmount();
	        }
	        return totalExpense / expenses.size();
	}

	private double getTotalIncome(Long userId) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, userId);

		if (user == null || user.getIncomes() == null) {
			return 0.0;
		}

		double totalIncome = 0.0;
		for (Income income : user.getIncomes()) {
			totalIncome += income.getAmount();
		}

		return totalIncome;
	}
}
