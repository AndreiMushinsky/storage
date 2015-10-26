SELECT start_balance, dr_movement, cr_movement, end_balance FROM 
	(SELECT IFNULL(dr, 0.00)-IFNULL(cr, 0.00) AS start_balance FROM 
		(SELECT SUM(amount) AS dr FROM Journal WHERE is_dr=1 AND date < :fromDate) AS Debit, 
		(SELECT SUM(amount) AS cr FROM Journal WHERE is_dr=0 AND date < :fromDate) AS Credit
	) AS Start, 
	(SELECT IFNULL(dr, 0.00) AS dr_movement, IFNULL(cr, 0.00) AS cr_movement FROM 
		(SELECT SUM(amount) AS dr FROM Journal WHERE is_dr=1 AND date BETWEEN :fromDate AND :toDate) AS DebitMovement, 
		(SELECT SUM(amount) AS cr FROM Journal WHERE is_dr=0 AND date BETWEEN :fromDate AND :toDate) AS CreditMovement
	) AS Mid, 
	(SELECT IFNULL(dr, 0.00)-IFNULL(cr, 0.00) AS end_balance FROM 
		(SELECT SUM(amount) AS dr FROM Journal WHERE is_dr=1 AND date <= :toDate) AS Debit, 
		(SELECT SUM(amount) AS cr FROM Journal WHERE is_dr=0 AND date <= :toDate) AS Credit
	) AS End