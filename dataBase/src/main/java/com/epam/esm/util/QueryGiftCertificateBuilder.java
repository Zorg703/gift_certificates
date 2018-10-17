package com.epam.esm.util;

import com.epam.esm.domain.GiftCertificate;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Sql query gift certificate builder.
 */
public class QueryGiftCertificateBuilder {
    private static final String GET_ALL = "SELECT g FROM GiftCertificate g";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String SORT_DATE = "g.creationDate";
    private static final String SORT_NAME = "g.name";
    private static final String GET_CERTIFICATES_BY_SEVERAL_TAGS = "SELECT g FROM GiftCertificate g inner join g.tags t WHERE g.state <> 'DELETED' and t.name in(:names) group by g.id having count(g.id)=:size";
    private static final String GET_CERTIFICATES_BY_SEVERAL_TAGS_AND_TEXT = "SELECT g FROM GiftCertificate g inner join g.tags t WHERE t.name in(:names) group by g.id having count(g.id)=:size and g.state <> 'DELETED' and (g.name like :name or g.description like :name)";
    private static final String GET_GIFT_CERTIFICATES_BY_PART_OF_DESCRIPTIONS_OR_NAME = "SELECT distinct g FROM GiftCertificate g WHERE g.state <> 'DELETED' and (g.name like :name or g.description like :name) ";
    private static final String NOT_DELETED=" where g.state <> 'DELETED'";

    private static String getSort(String sort) {
        if(sort!=null) {
            if (sort.equals("name")) {
                return SORT_NAME;
            }
            if (sort.equals("date")) {
                return SORT_DATE;
            }
        }
        return SORT_DATE;
    }


    public static Map<TypedQuery<GiftCertificate>,Integer> buildQuery(List<String> tags, String text, String sort, EntityManager entityManager ) {
        Map<TypedQuery<GiftCertificate>,Integer> map=new HashMap<>();
        sort = getSort(sort);
        TypedQuery<GiftCertificate> typedQuery;
        StringBuilder stringBuilder = new StringBuilder();
        String query;
        if(StringUtils.isEmpty(text) && !tags.isEmpty() ) {
            query = stringBuilder.append(GET_CERTIFICATES_BY_SEVERAL_TAGS).append(ORDER_BY).append(sort).toString();
            typedQuery = entityManager.createQuery(query, GiftCertificate.class);
            typedQuery.setParameter("names", tags);
            typedQuery.setParameter("size", (long) tags.size());
            Integer count=getCountInQuery(typedQuery);
            map.put(typedQuery,count);
            return map;
        }
        if(!StringUtils.isEmpty(text) && !tags.isEmpty()) {
            query = stringBuilder.append(GET_CERTIFICATES_BY_SEVERAL_TAGS_AND_TEXT).append(ORDER_BY).append(sort).toString();
            typedQuery = entityManager.createQuery(query, GiftCertificate.class);
            typedQuery.setParameter("names", tags);
            typedQuery.setParameter("size", (long) tags.size());
            typedQuery.setParameter("name", '%' + text + '%');
            Integer count=getCountInQuery(typedQuery);
            map.put(typedQuery,count);
            return map;
        }
        if(!StringUtils.isEmpty(text) && tags.isEmpty()) {
            query = stringBuilder.append(GET_GIFT_CERTIFICATES_BY_PART_OF_DESCRIPTIONS_OR_NAME).append(ORDER_BY).append(sort).toString();
            typedQuery = entityManager.createQuery(query, GiftCertificate.class);
            typedQuery.setParameter("name", '%' + text + '%');
            StringBuilder builder=new StringBuilder();
            String q="SELECT g FROM GiftCertificate g WHERE g.name like '"+'%'+text+'%'+"' or g.description like '" +'%'+text+'%'+"' and g.state <> 'DELETED'";
            q=builder.append(q).append(ORDER_BY).append(sort).toString();
            Integer count= Math.toIntExact(getCountInQuery(q, entityManager));
            map.put(typedQuery,count);
            return map;
        }
        if (StringUtils.isEmpty(text) && tags.isEmpty()) {
            query = stringBuilder.append(GET_ALL).append(NOT_DELETED).append(ORDER_BY).append(sort).toString();
            typedQuery=entityManager.createQuery(query, GiftCertificate.class);
            Integer count= Math.toIntExact(getCountInQuery(query, entityManager));
            map.put(typedQuery,count);
            return map;
        }
        return map;
    }

    private static Long getCountInQuery(String query,EntityManager entityManager){
        Query queryTotal = entityManager.createQuery
                ("Select count(g.id) from GiftCertificate g where g in ("+query+")");
        return  (Long) queryTotal.getSingleResult();
    }

    private static Integer getCountInQuery(TypedQuery query){
        return query.getResultList().size();
    }
}