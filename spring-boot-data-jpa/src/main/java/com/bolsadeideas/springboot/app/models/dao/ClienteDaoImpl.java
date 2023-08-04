package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ClienteDaoImpl implements IClienteDao {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		
		return em.find(Cliente.class, id);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		
		if(cliente.getId() != null && cliente.getId() > 0) {
			em.merge(cliente);
		}else {
			em.persist(cliente);
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		em.remove(findById(id));
		
	}

}
