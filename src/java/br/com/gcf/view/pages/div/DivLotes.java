/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div;

import br.com.gcf.model.dto.Fazenda_DTO;
import br.com.gcf.control.dao.Lote_DAO;
import br.com.gcf.control.threads.ReportBean;
import br.com.gcf.control.threads.ReportTask;
import br.com.gcf.model.dto.Lote_DTO;
import br.com.gcf.model.components.control.Button;
import br.com.gcf.model.components.control.ComboBox;
import br.com.gcf.model.components.control.EditLine;
import br.com.gcf.model.components.dialog.Loader;
import br.com.gcf.model.components.table.VirtualAbstractTableModel;
import br.com.gcf.model.components.table.VirtualModelLotes;
import br.com.gcf.view.Web;
import br.com.gcf.view.pages.div.dialog.DCLote;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.JSignal;
import eu.webtoolkit.jwt.Orientation;
import eu.webtoolkit.jwt.SelectionMode;
import eu.webtoolkit.jwt.Signal1;
import eu.webtoolkit.jwt.WAbstractItemView;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WHBoxLayout;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WLink;
import eu.webtoolkit.jwt.WTableView;
import eu.webtoolkit.jwt.WTemplate;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WVBoxLayout;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Windows
 */
public class DivLotes extends WContainerWidget implements Signal1.Listener<String> {

    private WVBoxLayout box;
    private WTemplate tempFlux;
    public static Signal1<String> signalLotes;
    private List<Fazenda_DTO> listaLotes;
    private WContainerWidget divCenter;
    private WContainerWidget divMain;
    private WVBoxLayout boxMain;
    private JSignal onclickedRight;
    private Web web;
    private WTableView tableView;
    private DCLote dialogLote;

    public DivLotes(Web web) {

        this.resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));
        this.web = web;
        this.init();
    }

    private void init() {

        WVBoxLayout box = new WVBoxLayout(this);
        box.setContentsMargins(5, 10, 5, 0);
        box.setSpacing(0);

        this.onclickedRight = new JSignal(this, "onclickedRight");

        this.divCenter = new WContainerWidget();
        this.divMain = new WContainerWidget();
        this.divMain.setOverflow(Overflow.OverflowAuto, Orientation.Vertical);

        this.listaLotes = new LinkedList<>();
        signalLotes = new Signal1<>();
        createControl();
        createTable();

        box.addWidget(divCenter, 0);
        box.addWidget(divMain, 1);

        signalLotes.addListener(this, this);

    }

    @Override
    public void remove() {
    
        ((VirtualAbstractTableModel)this.tableView.getModel()).clear();
         super.remove(); //To change body of generated methods, choose Tools | Templates.
    }

    private void createTable() {

        WVBoxLayout boxV = new WVBoxLayout(divMain);

        this.tableView = new WTableView();
        tableView.setSortingEnabled(true);
        tableView.setRowHeaderCount(2);
        tableView.setAlternatingRowColors(true);
        tableView.setRowHeight(new WLength(28));
        tableView.setHeaderHeight(new WLength(28));
        tableView.setSelectionMode(SelectionMode.SingleSelection);
        tableView.setEditTriggers(EnumSet.of(WAbstractItemView.EditTrigger.NoEditTrigger));
        tableView.setColumnWidth(0, new WLength(100, WLength.Unit.Pixel));
        tableView.setColumnWidth(1, new WLength(200, WLength.Unit.Pixel));
        tableView.setAttributeValue("oncontextmenu", "event.cancelBubble = true; event.returnValue = false; return false;");

        String[] header = new String[]{
            "Codigo".toUpperCase(),
            "Lote".toUpperCase(),
            "Data".toUpperCase(),
            "Peso Minimo".toUpperCase(),
            "Peso Medio".toUpperCase(),
            "Peso Maximo".toUpperCase(),
            "Peso Total".toUpperCase(),
            "Ração".toUpperCase(),
            "Peso Carcaça".toUpperCase(),
            "Arroba".toUpperCase(),
            "Apartações".toUpperCase(),
            "Animais".toUpperCase(),
            "Editar".toUpperCase(),
            "Remover".toUpperCase()
        };

        this.modelTable(header, tableView, false, 0);

        tableView.headerClicked().addListener(tableView, ((index, mouse) -> {

            if (tableView.isSortingEnabled(index)) {

                VirtualModelLotes mCliente = (VirtualModelLotes) tableView.getModel();
                this.modelTable(header, tableView, !mCliente.isIsSorting(), index);

                //      mCliente.iteretorDesc(mCliente.getMap());
            }

        }));

        tableView.clicked().addListener(tableView, ((index, mouse) -> {

            try {

                int row = index.getRow();
                int column = index.getColumn();

            } catch (Exception e) {
            }

        }));
        
        boxV.addWidget(tableView);
    }

    private void createControl() {

        WContainerWidget divControl = new WContainerWidget(this.divCenter);

        WHBoxLayout boxh = new WHBoxLayout(divControl);
        boxh.setContentsMargins(10, 0, 0, 10);
        boxh.setSpacing(5);

        WText labelFiltro = new WText("Filtro:");
        labelFiltro.setTextAlignment(AlignmentFlag.AlignMiddle);
        
        EditLine textFiltro = new EditLine();
        textFiltro.setPlaceholderText("Filtrar por");
        textFiltro.setMaximumSize(new WLength(250, WLength.Unit.Pixel),WLength.Auto);
        
        WTemplate btBuscar =  new WTemplate();
        btBuscar.setTemplateText("<button type=\"button\" class=\"btn btn-primary\">Filtrar</button>");
        
        ComboBox<String> comboSelector = new ComboBox<>(
                new String[]{
                    "Sem Filtro".toUpperCase(),
                    "Codigo".toUpperCase(),
                    "Lote".toUpperCase(),
                    "Data".toUpperCase(),
                    "Peso Minimo".toUpperCase(),
                    "Peso Medio".toUpperCase(),
                    "Peso Maximo".toUpperCase(),
                    "Peso Total".toUpperCase(),
                    "Ração".toUpperCase(),
                    "Peso Carcaça".toUpperCase(),
                    "Arroba".toUpperCase(),
                    "Apartações".toUpperCase(),
                    "Animais".toUpperCase()});
        comboSelector.setMaximumSize(new WLength(150, WLength.Unit.Pixel),WLength.Auto);
        
        Button btAdd = new Button("Adicionar", 10);
        btAdd.setIcon(new WLink("images/lote/farm.png"));

        Button btDeletar = new Button("Limpar", 10);
        btDeletar.setIcon(new WLink("images/fazenda/lixo.png"));

        btAdd.setMaximumSize(new WLength(140, WLength.Unit.Pixel), new WLength(40, WLength.Unit.Pixel));
        btDeletar.setMaximumSize(new WLength(140, WLength.Unit.Pixel), new WLength(40, WLength.Unit.Pixel));
      
        btAdd.clicked().addListener(btAdd, (mouse) -> {

            if (dialogLote == null) {

                this.dialogLote = new DCLote(web);

                this.dialogLote.getSignalClose().addListener(this.dialogLote, () -> {

                    this.dialogLote = null;

                });

            }

        });
        btAdd.setAttributeValue("oncontextmenu", onclickedRight.createCall() + "\nevent.cancelBubble = true; event.returnValue = false; return false;");

        boxh.addWidget(labelFiltro,0,AlignmentFlag.AlignMiddle);
        boxh.addWidget(textFiltro,0,AlignmentFlag.AlignMiddle);
        boxh.addWidget(btBuscar,0,AlignmentFlag.AlignMiddle);
        boxh.addWidget(comboSelector,1,AlignmentFlag.AlignMiddle);
        boxh.addWidget(btAdd, 0,AlignmentFlag.AlignMiddle,AlignmentFlag.AlignRight);
        boxh.addWidget(btDeletar,0,AlignmentFlag.AlignMiddle,AlignmentFlag.AlignRight);

    }

    public void trigger(String arg) {

        //cleat table
        this.divMain.clear();
        this.createTable();

        this.web.createMessageTemp(arg, Web.Tipo_Mensagem.SUCESSO);
    }

    private void modelTable(String[] header, WTableView tableView, boolean isSorting, int index) {

        Signal1 signalLotes = new Signal1();

        VirtualModelLotes<Lote_DTO> model = new VirtualModelLotes(web,0, header, tableView);
        model.setIsSorting(isSorting);
        tableView.setModel(model);

        Loader loader = new Loader(web);

        signalLotes.addListener(this, ((arg) -> {

            List<Lote_DTO> lotes = (List<Lote_DTO>) arg;

            if (lotes == null) {
                return;
            }
            try {

                for (int i = 0; i < lotes.size(); i++) {

                    Lote_DTO lote = lotes.get(i);
                    model.addTemplate(i, lote);

                    for (int j = 0; j < header.length; j++) {

                        switch (j) {

                            case 0: // codigo
                                model.insert(i, j, String.valueOf(lote.getId()));
                                break;
                            case 1: // lote
                                model.insert(i, j, lote.getNome());
                                break;
                            case 2: // data
                                model.insert(i, j, lote.getData());
                                break;
                            case 3: // peso minimo
                                model.insert(i, j, String.valueOf(lote.getPesoMinimo()));
                                break;
                            case 4: // peso medio
                                model.insert(i, j, String.valueOf(lote.getPesoMedio()));
                                break;
                            case 5: // peso maximo
                                model.insert(i, j, String.valueOf(lote.getPesoMaximo()));
                                break;
                            case 6: // peso total
                                model.insert(i, j, String.valueOf(lote.getPesoTotal()));
                                break;
                            case 7: // racao alimento
                                model.insert(i, j, String.valueOf(lote.getRacao().toString()));
                                break;
                            case 8: // peso da carcaça
                                model.insert(i, j, String.valueOf(lote.getPesoDaCarcaca()));
                                break;
                            case 9: // arroba
                                model.insert(i, j, String.valueOf(lote.getArroba()));
                                break;
                            case 10: // quantidade que possue apartacao
                                model.insert(i, j, String.valueOf(lote.getQuantidadeApartacao()));
                                break;
                            case 11: // quantidade de animais
                                model.insert(i, j, String.valueOf(lote.getQuantidade()));
                                break;
                            case 12: // quantidade de animais
                                model.insert(i, j, "");
                                break;
                            case 13: // remover
                                model.insert(i, j, "");
                                break;
                        }

                    }
                }

                model.updateTable();

            } finally {
                loader.destroy();
                tableView.setFocus();
            }

        }));

        ReportBean.runReports(new ReportTask<Signal1>(WApplication.getInstance(),signalLotes) {
            @Override
            public void run(){

                List<Lote_DTO> lotes = Lote_DAO.readAllLotesFazendas();

                this.beginLock();

                signal.trigger(lotes);

                this.endLock();
            }
        });
    }
}
