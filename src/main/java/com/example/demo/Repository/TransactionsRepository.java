package com.example.demo.Repository;

import com.example.demo.Model.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions,Integer> {

    Page<Transactions> findByStatusIsNot(String status, Pageable pageable);


    Page<Transactions> findByOrgCodeIn(Pageable pageable, List organizationCodes);

    Page<Transactions> findByOrgCodeInAndCreatedDateBetween(Pageable pageable, List organizationCodes, Date startDate, Date endDate);

    Page<Transactions> findByTxnIdEqualsOrOrgCodeEquals(Pageable pageable, String search, String search1);

    List<Transactions> findDistinctByOrgCodeIsNot(String aNull);

    List<Transactions> findByTxnIdEqualsOrOrgCodeEquals(String searchVariable, String searchVariable1);

    Page<Transactions> findByTxnIdBetween(Pageable pageable, String startDateIdIndex, String endDateIdIndex);

    Page<Transactions> findByOrgCodeInAndTxnIdBetween(Pageable pageable, List organizationCodes, String startDateIdIndex, String endDateIdIndex);


    List<Transactions> findByOrgCodeInAndTxnIdBetween(List<String> orgCodesList, String startDateIdIndex, String endDateIdIndex);

    List<Transactions> findByOrgCodeIn(List<String> orgCodesList);

    List<Transactions> findByTxnIdBetween(String startDateIdIndex, String endDateIdIndex);
}
