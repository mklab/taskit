package org.mklab.taskit.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.mklab.taskit.model.Test;

/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
public class TestDao {

	private EntityManager manager;

	public TestDao(EntityManager manager) {
		this.manager = manager;
	}

	public Test add(Test test) {
		EntityTransaction t = manager.getTransaction();
		t.begin();
		manager.persist(test);
		t.commit();

		return test;
	}

	@SuppressWarnings("unchecked")
	public List<Test> list() {
		Query q = manager.createQuery("select t from test as t");
		return (List<Test>) q.getResultList();
	}

}
