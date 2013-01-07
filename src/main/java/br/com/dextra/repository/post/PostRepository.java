package br.com.dextra.repository.post;

import java.util.ArrayList;
import java.util.Collections;

import br.com.dextra.persistencia.PostFields;
import br.com.dextra.repository.document.DocumentRepository;
import br.com.dextra.utils.Converters;
import br.com.dextra.utils.IndexFacade;
import br.com.dextra.utils.IndexKeys;
import br.com.dextra.utils.Data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortOptions;

public class PostRepository extends BaseRepository {

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private DocumentRepository postDoDocumentReository = new DocumentRepository();

	public Iterable<Entity> buscarTodosOsPosts(int maxResults, int offSet) {

		Query query = new Query(IndexKeys.POST.getKey());

		query.addSort(PostFields.DATA_DE_ATUALIZACAO.getField(),
				SortDirection.DESCENDING);
		PreparedQuery prepared = datastore.prepare(query);

		FetchOptions opts = FetchOptions.Builder.withDefaults();
		opts.limit(maxResults);
		opts.offset(offSet);

		return prepared.asIterable(opts);
	}

	public Iterable<Entity> buscarPosts(int maxResults, String q,
			int offset) throws EntityNotFoundException {

		ArrayList<String> listaDeIds = buscaIdsPostsFTS(maxResults, q, offset);

		ArrayList<Entity> listaResults = buscaEntitiesPost(listaDeIds);

		return listaResults;
	}

	private ArrayList<Entity> buscaEntitiesPost(
			ArrayList<String> listaDeIds) throws EntityNotFoundException {
		ArrayList<Entity> listaResults = new ArrayList<Entity>();

		for (String id : listaDeIds) {
			Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
			Entity e = datastore.get(key);
			listaResults.add(e);
		}
		return listaResults;
	}

	public ArrayList<String> buscaIdsPostsFTS(int maxResults, String q,
			int offset) {

		com.google.appengine.api.search.Query query = preparaQuery(q);

		ArrayList<String> listaDeIds = Converters
				.toListaDeIds(IndexFacade.getIndex(IndexKeys.POST.getKey())
						.search(query));

		// FIXME: Set limit dentro da query nao funciona
		return ListaDeIdsParaMostrarComOffsetForcado(maxResults, offset,
				listaDeIds);
	}

	private ArrayList<String> ListaDeIdsParaMostrarComOffsetForcado(
			int maxResults, int offset, ArrayList<String> listaDeIds) {
		Collections.reverse(listaDeIds);
		ArrayList<String> arrayTemp = new ArrayList<String>();
		int f = maxResults + offset;

		if (maxResults + offset > listaDeIds.size())
			f = listaDeIds.size();

		if(offset>listaDeIds.size())
			offset=listaDeIds.size();
		for (String string : listaDeIds.subList(offset, f)) {
			arrayTemp.add(string);
		}

		listaDeIds = arrayTemp;

		return listaDeIds;
	}

	private com.google.appengine.api.search.Query preparaQuery(
			String q) {

		SortOptions sortOptions = SortOptions.newBuilder().addSortExpression(
				SortExpression.newBuilder().setExpression(
						PostFields.DATA_DE_ATUALIZACAO.getField())
						.setDirection(SortExpression.SortDirection.ASCENDING)
						.setDefaultValue("0")).build();

		QueryOptions queryOptions = QueryOptions.newBuilder().setSortOptions(
				sortOptions).setFieldsToReturn(PostFields.ID.getField(),
				PostFields.DATA_DE_ATUALIZACAO.getField(),PostFields.TITULO.getField()).setLimit(1000).build();

		com.google.appengine.api.search.Query query = com.google.appengine.api.search.Query
				.newBuilder().setOptions(queryOptions).build(q);
		return query;
	}

	public Entity criaNovoPost(String titulo, String conteudo, String usuario) {

		String id = Data.geraID();
		Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
		String data = new Data().pegaData();

		return this.criaNovoPost(titulo, conteudo, usuario, id, key,
				data);
	}

	public Entity criaNovoPost(String titulo, String conteudo,
			String usuario, String id, Key key, String data) {

		Entity valueEntity = criaEntityPost(titulo, conteudo, usuario, id, key,
				data);
		persist(valueEntity);

		postDoDocumentReository.criarDocumentPost(titulo, conteudo, usuario, id,
				data);

		return valueEntity;
	}

	private Entity criaEntityPost(String titulo, String conteudo,
			String usuario, String id, Key key, String data) {
		Entity valueEntity = new Entity(key);
		valueEntity.setProperty(PostFields.ID.getField(), id);
		valueEntity.setProperty(PostFields.TITULO.getField(), titulo);

		valueEntity.setProperty(PostFields.CONTEUDO.getField(), new Text(
				conteudo));
		valueEntity.setProperty(PostFields.USUARIO.getField(), usuario);
		valueEntity.setProperty(PostFields.COMENTARIO.getField(), 0);
		valueEntity.setProperty(PostFields.LIKES.getField(), 0);
		valueEntity.setProperty(PostFields.DATA.getField(), data);
		valueEntity
				.setProperty(PostFields.DATA_DE_ATUALIZACAO.getField(), data);

		return valueEntity;
	}

	public void alteraDatadaEntity(String id, String data) throws EntityNotFoundException {

		Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
		Entity valueEntity = datastore.get(key);
		valueEntity
				.setProperty(PostFields.DATA_DE_ATUALIZACAO.getField(), data);

		persist(valueEntity);
	}

	public void incrementaNumeroDeComentariosDaEntityDoPost(String id) throws EntityNotFoundException
	{

		Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
		Entity valueEntity = datastore.get(key);
		int comments=Integer.parseInt(valueEntity.getProperty(PostFields.COMENTARIO.getField()).toString());
		valueEntity
				.setProperty(PostFields.COMENTARIO.getField(), comments+1);
		persist(valueEntity);
	}

	public void umPostFoiComentado(String id) throws EntityNotFoundException
	{
		String data = new Data().pegaData();;
		postDoDocumentReository.alteraDatadoDocumento(id,data);
		this.alteraDatadaEntity(id, data);

		incrementaNumeroDeComentariosDaEntityDoPost(id);

	}

	public void remove(String id) {
		Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);

		try {
			datastore.delete(key);
		} finally {
			DocumentRepository indexacao = new DocumentRepository();
			indexacao.removeIndex(IndexKeys.POST.getKey(), id);
		}
	}


	public Entity obtemPorId(String id) throws EntityNotFoundException  {
		Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
		return datastore.get(key);
	}
}
