SELECT name, start_balance, dr_movement, cr_movement, start_balance+dr_movement-cr_movement AS end_balance FROM 
	(SELECT id, start_balance, dr_movement, IFNULL(cr, 0.00) AS cr_movement FROM 
		(SELECT id, start_balance, IFNULL(dr, 0.00) AS dr_movement FROM 
			(SELECT id, dr-IFNULL(cr, 0.00) as start_balance FROM 
				(SELECT id, IFNULL(dr, 0.00) AS dr FROM Fabrics LEFT JOIN 
					(SELECT fabric_id, SUM(amount) AS dr FROM Journal WHERE is_dr = 1 AND date < ? GROUP BY fabric_id) AS T1 
				ON id = fabric_id) AS T2 
			LEFT JOIN 
				(SELECT fabric_id, SUM(amount) AS cr FROM Journal WHERE is_dr = 0 AND date < ? GROUP BY fabric_id) AS T3 
			ON id = fabric_id) AS T4 
		LEFT JOIN 
			(SELECT fabric_id, SUM(amount) AS dr FROM Journal WHERE is_dr=1 AND date BETWEEN ? AND ? GROUP BY fabric_id) AS T5 
		ON id = fabric_id) AS T6 
	LEFT JOIN 
		(SELECT fabric_id, SUM(amount) AS cr FROM Journal WHERE is_dr=0 AND date BETWEEN ? AND ? GROUP BY fabric_id) AS T7 
	ON id = fabric_id) 
AS T8, Fabrics WHERE T8.id = Fabrics.id;