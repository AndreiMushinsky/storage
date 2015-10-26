var rootPath = $('script[src*=journal]')
						.attr('src')
						.replace('/resources/script/journal.js', '');

var imagePath = rootPath + "/resources/img/";

var edit = $("<a></a>")
	.append($("<img></img>").attr("src", imagePath+"edit.png").attr("class", "button").attr("id", "edit"));
var del = $("<a></a>").attr("onclick", "return confirm('Вы действительно хотите удалить эту запись?')")
	.append($("<img></img>").attr("src", imagePath+"del.png").attr("class", "button").attr("id", "delete"));

$(document).ready(function(){
    $(".entry").on({
    	click: function(){
    		$("#selectedRow").removeAttr("id");
    		$(this).attr("id", "selectedRow");
    		$(".control").append(edit);
    		$(".control").append(del);
    		var id = $("#selectedRow > .entryId").text();
    		del.attr("href", rootPath + "/remove_entry?id="+ id);
    		edit.attr("href", rootPath + "/edit_entry?id="+ id);
    	}
    });
    $("#add").on({
    	mouseenter: function(){
    		$(this).hide();
    		$(".hidden_ref").show();	
    	}
    });
    $(".control").on({
    	mouseleave: function(){
    		$(".hidden_ref").hide();
    		$("#add").show();	
    	}
    });
}); 