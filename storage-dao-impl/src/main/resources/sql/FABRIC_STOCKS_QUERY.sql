SELECT name, dr-cr AS balance FROM
  	(SELECT fabric_id, name, SUM(amount) as dr FROM Journal, Fabrics 
  		WHERE is_dr=1 AND Journal.fabric_id = Fabrics.id GROUP BY fabric_id) as Debit
	LEFT JOIN
	(SELECT fabric_id, SUM(amount) as cr from Journal where is_dr=0 GROUP BY fabric_id) as Credit
	ON (Debit.fabric_id = Credit.fabric_id)