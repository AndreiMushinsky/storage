SELECT dr-cr AS balance FROM
	(SELECT IFNULL(SUM(amount), 0.00) AS dr FROM Journal WHERE is_dr=1 AND fabric_id=?) AS Debit,
	(SELECT IFNULL(SUM(amount), 0.00) AS cr FROM Journal WHERE is_dr=0 AND fabric_id=?) AS Credit;