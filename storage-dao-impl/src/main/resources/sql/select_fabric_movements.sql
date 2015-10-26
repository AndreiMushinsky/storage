SELECT name, start_balance, dr_movement, cr_movement, (start_balance + dr_movement - cr_movement) AS end_balance 
	FROM Fabrics, 
		(SELECT id, start_balance, dr_movement, IFNULL(cr_movement, 0.00) AS cr_movement 
			FROM (SELECT id, start_balance, IFNULL(dr_movement, 0.00) AS dr_movement 
				FROM (SELECT id, dr_start - IFNULL(cr_start, 0.00) AS start_balance 
					FROM (SELECT id, IFNULL(dr_start, 0.00) as dr_start 
						FROM Fabrics 
						LEFT JOIN (SELECT fabric_id, SUM(amount) as dr_start 
							FROM Journal WHERE date < :fromDate AND is_dr = true GROUP BY fabric_id) AS T1 
						ON id = fabric_id) AS T2 
					LEFT JOIN (SELECT fabric_id, SUM(amount) as cr_start 
						FROM Journal WHERE date < :fromDate AND is_dr = false GROUP BY fabric_id) AS T3 
					ON id = fabric_id) AS T4 
				LEFT JOIN (SELECT fabric_id, SUM(amount) AS dr_movement 
					FROM Journal WHERE is_dr = true AND date BETWEEN :fromDate AND :toDate GROUP BY fabric_id) AS T5 
				ON id = fabric_id) AS T6 
			LEFT JOIN (SELECT fabric_id, SUM(amount) AS cr_movement 
				FROM Journal WHERE is_dr = false AND date BETWEEN :fromDate AND :toDate GROUP BY fabric_id) AS T7 
			ON id = fabric_id) AS T8 
WHERE Fabrics.id = T8.id ORDER BY name ASC;
