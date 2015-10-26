SELECT name, IFNULL(balance, 0.00) AS balance FROM
	Fabrics	LEFT JOIN 
	(SELECT Debit.fabric_id, dr-IFNULL(cr, 0.00) as balance FROM
  		(SELECT fabric_id, SUM(amount) as dr FROM Journal WHERE is_dr=1 GROUP BY fabric_id) as Debit
			LEFT JOIN
		(SELECT fabric_id, SUM(amount) as cr from Journal where is_dr=0 GROUP BY fabric_id) as Credit
			ON Debit.fabric_id = Credit.fabric_id) as Balance
	ON Fabrics.id = Balance.fabric_id ORDER BY name ASC