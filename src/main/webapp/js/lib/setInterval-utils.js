setIntervalUtils = {

	formataData : function (data){
		d = data.split(" ");
		dia = d[0].split("/");
		hora = d[1].split(":");
		//Date(yyyy, mm, dd, hh, mm, ss)
		return data = new Date(dia[2],dia[1] - 1,dia[0],hora[0],hora[1], 59);

	},

	verificaPosts : function (data){
		setInterval(function() {
			dextranet.post.verificaNovos(setIntervalUtils.formataData(ultimoPost));
		}, dextranet.settings.intervaloBuscaNovosPosts);

	},

	verificaMicroBlog : function (data){
		setInterval(function() {
			dextranet.microblog.verificaNovos(setIntervalUtils.formataData(ultimoMicroPost));
		}, dextranet.settings.intervaloBuscaNovosPosts);

	}
}