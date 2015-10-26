SELECT SUM(dr)-SUM(cr) FROM 
	(SELECT fabric_id, SUM(amount) as dr FROM Journal where is_dr=1 GROUP BY fabric_id) as Debit
	LEFT JOIN	
	(SELECT fabric_id, SUM(amount) as cr from Journal WHERE is_dr=0 GROUP BY fabric_id) as Credit 
	ON (Debit.fabric_id = Credit.fabric_id)