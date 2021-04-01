package com.arpiatecnologia.repository;

import com.arpiatecnologia.filter.ClienteFilter;
import com.arpiatecnologia.model.Cliente;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClienteQueryRepositoryImpl implements ClienteQueryRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Cliente> readAll(ClienteFilter filter) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT \n");
        jpql.append("   cliente \n");
        jpql.append("FROM Cliente cliente \n");

        if(StringUtils.hasText(filter.getNome())) {
            jpql.append("WHERE nome like :nome");
        }

        TypedQuery<Cliente> typedQuery = this.em.createQuery(jpql.toString(), Cliente.class);

        if(StringUtils.hasText(filter.getNome())) {
            typedQuery.setParameter("nome", "%" + filter.getNome() + "%");
        }

        return typedQuery.getResultList();
    }
}
