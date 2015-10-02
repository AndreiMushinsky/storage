SELECT start_balance, dr_movement, cr_movement, end_balance FROM 
	(SELECT IFNULL(dr, 0.00)-IFNULL(cr, 0.00) AS start_balance FROM 
		(SELECT SUM(amount) AS dr FROM Journal WHERE is_dr=1 AND date < ?) AS Debit, 
		(SELECT SUM(amount) AS cr FROM Journal WHERE is_dr=0 AND date < ?) AS Credit
	) AS Start, 
	(SELECT IFNULL(dr, 0.00) AS dr_movement, IFNULL(cr, 0.00) AS cr_movement FROM 
		(SELECT SUM(amount) AS dr FROM Journal WHERE is_dr=1 AND date BETWEEN ? AND ?) AS DebitMovement, 
		(SELECT SUM(amount) AS cr FROM Journal WHERE is_dr=0 AND date BETWEEN ? AND ?) AS CreditMovement
	) AS Mid, 
	(SELECT IFNULL(dr, 0.00)-IFNULL(cr, 0.00) AS end_balance FROM 
		(SELECT SUM(amount) AS dr FROM Journal WHERE is_dr=1 AND date <= ?) AS Debit, 
		(SELECT SUM(amount) AS cr FROM Journal WHERE is_dr=0 AND date <= ?) AS Credit
	) AS End;