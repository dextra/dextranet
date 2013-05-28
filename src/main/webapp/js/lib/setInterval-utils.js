setIntervalUtils = {

	verificaPosts : function (data){
		setInterval(function() {
			d = ultimoPost.split(" ");
			dia = d[0].split("/");
			hora = d[1].split(":");
			//Date(yyyy, mm, dd, hh, mm, ms)
			data = new Date(dia[2],dia[1] - 1,dia[0],hora[0],hora[1]);
			dextranet.post.contar(data);
		}, dextranet.settings.intervaloBuscaNovosPosts);

	},

	verificaMicroBlog : function (data){
		setInterval(function() {
			d = ultimoMicroPost.split(" ");
			dia = d[0].split("/");
			hora = d[1].split(":");
			//Date(yyyy, mm, dd, hh, mm, ss, ms)
			data = new Date(dia[2],dia[1] - 1,dia[0],hora[0],hora[1]);
			dextranet.microblog.contar(data);
		}, dextranet.settings.intervaloBuscaNovosPosts);

	}
}