$(function() {
	$.datepicker.regional['ru'] = {
		closeText : 'Закрыть',
		prevText : '&#x3c;Пред',
		nextText : 'След&#x3e;',
		currentText : 'Сегодня',
		monthNames : [ 'Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь',
				'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь' ],
		monthNamesShort : [ 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл',
				'Авг', 'Сен', 'Окт', 'Ноя', 'Дек' ],
		dayNames : [ 'воскресенье', 'понедельник', 'вторник', 'среда',
				'четверг', 'пятница', 'суббота' ],
		dayNamesShort : [ 'вск', 'пнд', 'втр', 'срд', 'чтв', 'птн', 'сбт' ],
		dayNamesMin : [ 'Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб' ],
		dateFormat : 'dd.mm.yy',
		firstDay : 1,
		isRTL : false
	};
	$.datepicker.setDefaults($.datepicker.regional['ru']);
	$(".date").datepicker({
		dateFormat : "dd.mm.yy"
	});

});

function checkTimePeriod(form) {
	var fromDate = new Date(form.fromDate.value.replace(
			/(\d{2}).(\d{2}).(\d{4})/, "$2/$1/$3"));
	var toDate = new Date(form.toDate.value.replace(/(\d{2}).(\d{2}).(\d{4})/,
			"$2/$1/$3"));
	if (toDate < fromDate) {
		document.getElementById("errorMessage").innerHTML = "ОШИБКА: вторая дата раньше первой.";
		return false;
	}
	return true;
}