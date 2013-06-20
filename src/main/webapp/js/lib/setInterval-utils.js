setIntervalUtils = {

	formataData : function (data){
		if(data){
			d = data.split(" ");
			dia = d[0].split("/");
			hora = d[1].split(":");
			//Date(yyyy, mm, dd, hh, mm, ss)
			data = new Date(dia[2],dia[1] - 1,dia[0],hora[0],hora[1],hora[2]);
			return data;
		}
	},

	timerMicroPosts : null,

	verificaMicroBlog : function (data){
		if(!setIntervalUtils.timerMicroPosts && data){
			setIntervalUtils.timerMicroPosts = setInterval(function() {
				dextranet.microblog.verificaNovos(setIntervalUtils.formataData(ultimoMicroPost));
			}, dextranet.settings.intervaloBuscaNovosPosts);
		}
	}
}