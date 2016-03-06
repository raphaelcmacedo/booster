package com.booster.controller;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import com.booster.entity.GenericEntity;
import com.booster.service.GenericService;

public abstract class GenericCrudMB<Entity extends GenericEntity, Service extends GenericService> implements Serializable {
	/**
	 * Constantes para serem usadas como chave (nome) a ser armazenado no
	 * pageFlowScope o resultado das consultas.
	 */
	public static final String SEARCH_PARAMETERS = "searchParameters";

	/**
	 * Estados para ajudar os componentes a saberem em que estado estб o fluxo.
	 */
	public static final String STATE_EDIT = "edit";
	public static final String STATE_INSERT = "insert";
	public static final String STATE_DELETE = "delete";
	public static final String STATE_VIEW = "view";
	public static final String STATE_SEARCH = "search";

	protected static final Map<String, String> titles = new HashMap<String, String>();
	static {
		titles.put(null, "Consultar"); // primeiro acesso a pбgina
		titles.put(STATE_SEARCH, "Consultar");
		titles.put(STATE_INSERT, "Inserir");
		titles.put(STATE_EDIT, "Editar");
		titles.put(STATE_VIEW, "Visualizar");
		titles.put(STATE_DELETE, "Excluir");
	}

	private Entity entity;
	
	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	private DataTable tblData;
	private List<GenericEntity> dataProvider;

	public List<GenericEntity> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(List<GenericEntity> dataProvider) {
		this.dataProvider = dataProvider;
	}

	/**
	 * Mensagem para quando o usuбrio nгo escolher nenhum item.
	 */
	protected String noItemSelectedMessage = "Nenhum item selecionado.";
	/**
	 * Mensagem para quando a busca nгo retornar nenhum resultado.
	 */
	protected String noResultMessage = "Não foram encontrados registros com esse critério.";
	
	/**
	 * Interface com a camada de negуcios.
	 */
	private Service service;

	protected GenericCrudMB(Service service) {
		this();
		this.service = service;
	}

	@SuppressWarnings("unchecked")
	public GenericCrudMB() {
		super();
	}

	@SuppressWarnings("unchecked")
	protected Entity getNewEntityInstance() {
		try {
			return (Entity) ((Class) ((java.lang.reflect.ParameterizedType) this
					.getClass().getGenericSuperclass())
					.getActualTypeArguments()[0]).newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	@PostConstruct
	public void init() {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			setCurrentState(null);
			search(null);
			
			if(this.getEntity() == null)
			{
				this.setEntity(getNewEntityInstance());
			}
		}
	}

	

	/**
	 * Implementa a aзгo de consulta. Disparado por um evento (actionListener)
	 * do UIXCommandButton (
	 * <tr:commandButton/>
	 * ).
	 * 
	 * @param event
	 *            Objeto que represento o evento.
	 */
	public void search(ActionEvent event) {

		Object parameters = FacesContext.getCurrentInstance().getAttributes().get(SEARCH_PARAMETERS);
		if (parameters != null) {
			dataProvider = service.list(parameters.toString());
		} else {
			dataProvider = service.list();
		}

		if (dataProvider.size() <= 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							noResultMessage, null));
		}
		setCurrentState(STATE_SEARCH);

	}

	// Save data
	public void save(ActionEvent event) {
		// inclusгo
		setEntity((Entity) service.save(getEntity()));

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Item incluido com sucesso."));

		// refaz a busca e volta para o estado de consultar
		search(event);
	}

	
	public void delete(ActionEvent event) {
		setCurrentState(STATE_DELETE);
		
		service.delete(getEntity());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Item excluído com sucesso."));
		// refaz a busca
		search(event);

		setCurrentState(STATE_SEARCH);
	}
	
		/**
	 * Cancela a inclusгo / alteraзгo.
	 * 
	 * @param event
	 */
	public void cancel(ActionEvent event) {
		// Cancelar as operaзхes, volta para a consulta
		setCurrentState(STATE_SEARCH);
		// limpa dos dados dos componentes de ediзгo
		// remove a entidade selecionada colocando uma nova, vazia no lugar
		setEntity(getNewEntityInstance());
	}

	/**
	 * Limpa filtro de pesquisa
	 */

	/**
	 * Remove os dados armazenados na fase Apply Request Values para o
	 * componente de id igual a <code>idComp</code>.
	 * 
	 * @param nomeComp
	 */
	protected void cleanSubmittedValues(String idComp) {
		UIComponent component = FacesContext.getCurrentInstance().getViewRoot()
				.findComponent(idComp);
		if (component != null)
			cleanSubmittedValues(component);
	}

	/**
	 * Limpa os dados dos componentes de ediзгo e de seus filhos,
	 * recursivamente.
	 * 
	 * Checa se o componente й instвncia de EditableValueHolder e 'reseta' suas
	 * propriedades.
	 * 
	 * @param component
	 */
	protected void cleanSubmittedValues(UIComponent component) {
		if (component instanceof EditableValueHolder) {
			EditableValueHolder evh = (EditableValueHolder) component;
			evh.setSubmittedValue(null);
			evh.setLocalValueSet(false);
			evh.setValid(true);
		}
		if (component.getChildCount() > 0) {
			for (UIComponent child : component.getChildren()) {
				cleanSubmittedValues(child);
			}
		}
	}

	/**
	 * Retorna o estado do fluxo do cadastro.
	 * 
	 * @return
	 */
	public String getCurrentState() {
		return (String) RequestContext.getCurrentInstance().getAttributes()
				.get(this.getClass() + "currentState");
	}

	/**
	 * Atribui o estado do fluxo do cadastro, apenas acessado internamente.
	 * 
	 * @param state
	 */
	protected void setCurrentState(String state) {
		RequestContext.getCurrentInstance().getAttributes()
		.put(this.getClass() + "currentState", state);
	}

	/**
	 * Retorna um componente que possua o id igual a <code>compId</code>.
	 * 
	 * @param compId
	 *            Id do componente no ViewRoot
	 * @return um componente que possua o id igual a <code>compId</code>.
	 */
	protected UIComponent findComponent(String compId) {
		return FacesContext.getCurrentInstance().getViewRoot()
				.findComponent(compId);
	}

	public DataTable getTblData() {
		return tblData;
	}

	public void setTblData(DataTable tblData) {
		this.tblData = tblData;
	}

	public String getNoItemSelectedMessage() {
		return noItemSelectedMessage;
	}

	public void setNoItemSelectedMessage(String noItemSelectedMessage) {
		this.noItemSelectedMessage = noItemSelectedMessage;
	}

	public String getNoResultMessage() {
		return noResultMessage;
	}

	public void setNoResultMessage(String noResultMessage) {
		this.noResultMessage = noResultMessage;
	}
	
	@SuppressWarnings("unchecked")
	public Entity getSearchObject() {
		return (Entity) RequestContext.getCurrentInstance().getAttributes()
				.get(this.getUniqueVariableName(SEARCH_PARAMETERS));
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	/**
	 * Retorna <code>true</code> se o estado estiver em editing.
	 * 
	 * @return
	 */
	public boolean isEditing() {
		return STATE_EDIT.equals(getCurrentState());
	}

	/**
	 * Retorna <code>true</code> se o estado estiver em inserзгo.
	 * 
	 * @return
	 */
	public boolean isInserting() {
		return STATE_INSERT.equals(getCurrentState());
	}

	/**
	 * Retorna <code>true</code> se o estado estiver em exclusгo.
	 * 
	 * @return
	 */
	public boolean isDeleting() {
		return STATE_DELETE.equals(getCurrentState());
	}

	/**
	 * Retorna <code>true</code> se o estado estiver em visualizaзгo.
	 * 
	 * @return
	 */
	public boolean isViewing() {
		return STATE_VIEW.equals(getCurrentState());
	}

	/**
	 * Retorna <code>true</code> se o estado estiver em atualizaзгo, ou seja,
	 * caso o estado esteja em ediзгo ou inserзгo ou exclusгo.
	 * 
	 * @return
	 */
	public boolean isUpdating() {
		return (this.isEditing() || this.isInserting() || this.isDeleting());
	}

	/**
	 * Retorna <code>true</code> se o estado estiver em busca.
	 * 
	 * @return
	 */
	public boolean isSearching() {
		return (getCurrentState() == null || STATE_SEARCH
				.equals(getCurrentState()));
	}

	/**
	 * Retorna tнtulo da pбgina atual
	 * 
	 * @return
	 */
	public String getTitleFromThePage() {
		final String currentState = this.getCurrentState();// == null ?
		// STATE_SEARCH :
		// this.getCurrentState();
		return titles.get(currentState);
	}

	/**
	 * Retorna uma chave ъnica para os parвmetros deste bean armazenados no
	 * PageFlowScope
	 */
	protected String getUniqueVariableName(String key) {
		String var = this.getClass().getSimpleName() + key;
		return var;
	}
}
