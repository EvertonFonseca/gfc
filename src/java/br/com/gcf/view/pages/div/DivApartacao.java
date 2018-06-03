/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div;

import br.com.gcf.control.dao.Apartacao_DAO;
import br.com.gcf.control.threads.ReportBean;
import br.com.gcf.control.threads.ReportTask;
import br.com.gcf.model.components.control.Button;
import br.com.gcf.model.components.control.ComboBox;
import br.com.gcf.model.components.control.EditLine;
import br.com.gcf.model.components.dialog.Loader;
import br.com.gcf.model.components.table.VirtualAbstractTableModel;
import br.com.gcf.model.components.table.VirtualModelApartacao;
import br.com.gcf.model.components.table.VirtualModelLotes;
import br.com.gcf.model.dto.Apartacao_DTO;
import br.com.gcf.view.Web;
import br.com.gcf.view.pages.div.dialog.DCApartacao;
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
import java.util.List;

/**
 *
 * @author Windows
 */
public class DivApartacao extends WContainerWidget {

    private WVBoxLayout box;
    private WTemplate tempFlux;
    private WContainerWidget divCenter;
    private WContainerWidget divMain;
    private WVBoxLayout boxMain;
    private JSignal onclickedRight;
    private Web web;
    private WTableView tableView;
    private DCApartacao dialogApartacao = null;

    public DivApartacao(Web web) {

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

        createControl();
        createTable();

        box.addWidget(divCenter, 0);
        box.addWidget(divMain, 1);

    }

    @Override
    public void remove() {

        ((VirtualAbstractTableModel) this.tableView.getModel()).clear();
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
        tableView.setSelectionMode(SelectionMode.ExtendedSelection);
        tableView.setEditTriggers(EnumSet.of(WAbstractItemView.EditTrigger.NoEditTrigger));
        tableView.setColumnWidth(0, new WLength(100, WLength.Unit.Pixel));
        tableView.setColumnWidth(1, new WLength(200, WLength.Unit.Pixel));
        tableView.setAttributeValue("oncontextmenu", "event.cancelBubble = true; event.returnValue = false; return false;");

        String[] header = new String[]{
            "Codigo".toUpperCase(),
            "Apartação".toUpperCase(),
            "Lote".toUpperCase(),
            "Tipo".toUpperCase(),
            "De".toUpperCase(),
            "Até".toUpperCase(),
            "Referencia".toUpperCase(),
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

                System.out.println("Selection: " + row + " " + column);

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
        textFiltro.setMaximumSize(new WLength(250, WLength.Unit.Pixel), WLength.Auto);

        WTemplate btBuscar = new WTemplate();
        btBuscar.setTemplateText("<button type=\"button\" class=\"btn btn-primary\">Filtrar</button>");

        ComboBox<String> comboSelector = new ComboBox<>(
                new String[]{
                    "Sem Filtro".toUpperCase(),
                    "Codigo".toUpperCase(),
                    "Apartação".toUpperCase(),
                    "Lote".toUpperCase(),
                    "Tipo".toUpperCase(),
                    "De".toUpperCase(),
                    "Até".toUpperCase(),
                    "Referencia".toUpperCase()});

        comboSelector.setMaximumSize(new WLength(150, WLength.Unit.Pixel), WLength.Auto);

        btBuscar.clicked().addListener(btBuscar, (mouse) -> {

            if (comboSelector.getCurrentIndex() == 0) {

                web.createMessageTemp("Nenhum campo foi definido para o filtro!", Web.Tipo_Mensagem.AVISO);
                return;
            }
            if (textFiltro.getText().isEmpty()) {

                this.modelTableFiltro((VirtualModelLotes<Apartacao_DTO>) tableView.getModel(), "", "", true);

            } else {

                String condicao = textFiltro.getText();
                String campo = comboSelector.getCurrentText().toString();
                this.modelTableFiltro((VirtualModelLotes<Apartacao_DTO>) tableView.getModel(), campo, condicao, false);
            }
        });

        Button btAdd = new Button("Adicionar", 10);
        btAdd.setIcon(new WLink("images/apartacao/cerca_nova.png"));

        Button btDeletar = new Button("Limpar", 10);
        btDeletar.setIcon(new WLink("images/fazenda/lixo.png"));

        btAdd.setMaximumSize(new WLength(140, WLength.Unit.Pixel), new WLength(55, WLength.Unit.Pixel));
        btDeletar.setMaximumSize(new WLength(140, WLength.Unit.Pixel), new WLength(55, WLength.Unit.Pixel));

        onclickedRight.addListener(this, () -> {

        });
        btAdd.clicked().addListener(divControl, (mouse) -> {

            if (this.dialogApartacao == null) {

                this.dialogApartacao = new DCApartacao(web);

                this.dialogApartacao.getSignalInsert().addListener(dialogApartacao, (arg) -> {

                    //cleat table
                    this.divMain.clear();
                    this.createTable();

                    this.web.createMessageTemp("Apartação inserido com sucesso!", Web.Tipo_Mensagem.SUCESSO);

                });

                this.dialogApartacao.getSignalClose().addListener(this.dialogApartacao, () -> {

                    this.dialogApartacao = null;

                });
            }

        });
        btAdd.setAttributeValue("oncontextmenu", onclickedRight.createCall() + "\nevent.cancelBubble = true; event.returnValue = false; return false;");

        boxh.addWidget(labelFiltro, 0, AlignmentFlag.AlignMiddle);
        boxh.addWidget(textFiltro, 0, AlignmentFlag.AlignMiddle);
        boxh.addWidget(btBuscar, 0, AlignmentFlag.AlignMiddle);
        boxh.addWidget(comboSelector, 1, AlignmentFlag.AlignMiddle);
        boxh.addWidget(btAdd, 0, AlignmentFlag.AlignMiddle, AlignmentFlag.AlignRight);
        boxh.addWidget(btDeletar, 0, AlignmentFlag.AlignMiddle, AlignmentFlag.AlignRight);

    }

    private void modelTable(String[] header, WTableView tableView, boolean isSorting, int index) {

        VirtualModelApartacao<Apartacao_DTO> model = new VirtualModelApartacao(web, 0, header, tableView);
        model.setIsSorting(isSorting);
        tableView.setModel(model);

        Signal1 signalApartacao = new Signal1();
        Loader loader = new Loader(web);

        signalApartacao.addListener(this, (args) -> {

            List<Apartacao_DTO> apartacoes = (List<Apartacao_DTO>) args;

            if (apartacoes == null) {
                return;
            }
            try {

                for (int i = 0; i < apartacoes.size(); i++) {

                    Apartacao_DTO apartacao = apartacoes.get(i);
                    model.addTemplate(i, apartacao);

                    for (int j = 0; j < header.length; j++) {

                        switch (j) {

                            case 0: // codigo
                                model.insert(i, j, String.valueOf(apartacao.getId()));
                                break;
                            case 1: // apartacao
                                model.insert(i, j, apartacao.getNome());
                                break;
                            case 2: // Lote
                                model.insert(i, j, apartacao.getLote().getNome());
                                break;
                            case 3: // tipo
                                model.insert(i, j, apartacao.getTipo().getNome());
                                break;
                            case 4: // de
                                model.insert(i, j, apartacao.getDe());
                                break;
                            case 5: // ate
                                model.insert(i, j, apartacao.getAte());
                                break;
                            case 6: // referencia
                                model.insert(i, j, apartacao.getTipo().getReferencia());
                                break;
                            case 7: // editar
                                model.insert(i, j, "");
                                break;
                            case 8: // remover
                                model.insert(i, j, "");
                                break;
                        }

                    }
                }
                model.updateTable();
            } finally {

                loader.destroy();
            }
        });

        ReportBean.runReports(new ReportTask<Signal1>(WApplication.getInstance(), signalApartacao) {
            @Override
            public void run() {

                List<Apartacao_DTO> apartacoes = Apartacao_DAO.readAllApartacoes();

                this.beginLock();

                signal.trigger(apartacoes);

                this.endLock();

            }
        });
    }

    private void modelTableFiltro(VirtualModelLotes<Apartacao_DTO> model, String campo, String condicao, boolean isAll) {

        Signal1 signalApartacao = new Signal1();

        Loader loader = new Loader(web);

        signalApartacao.addListener(this, (args) -> {

            List<Apartacao_DTO> apartacoes = (List<Apartacao_DTO>) args;

            if (apartacoes == null) {
                return;
            }
            try {

                for (int i = 0; i < apartacoes.size(); i++) {

                    Apartacao_DTO apartacao = apartacoes.get(i);
                    model.addTemplate(i, apartacao);

                    for (int j = 0; j < model.getHeaderNamesColumns().length; j++) {

                        switch (j) {

                            case 0: // codigo
                                model.insert(i, j, String.valueOf(apartacao.getId()));
                                break;
                            case 1: // apartacao
                                model.insert(i, j, apartacao.getNome());
                                break;
                            case 2: // Lote
                                model.insert(i, j, apartacao.getLote().getNome());
                                break;
                            case 3: // tipo
                                model.insert(i, j, apartacao.getTipo().getNome());
                                break;
                            case 4: // de
                                model.insert(i, j, apartacao.getDe());
                                break;
                            case 5: // ate
                                model.insert(i, j, apartacao.getAte());
                                break;
                            case 6: // referencia
                                model.insert(i, j, apartacao.getTipo().getReferencia());
                                break;
                            case 7: // editar
                                model.insert(i, j, "");
                                break;
                            case 8: // remover
                                model.insert(i, j, "");
                                break;
                        }

                    }
                }
                model.updateTable();
            } finally {

                loader.destroy();
            }
        });

        ReportBean.runReports(new ReportTask<Signal1>(WApplication.getInstance(), signalApartacao) {
            @Override
            public void run() {

                List<Apartacao_DTO> ap = null;
                if (!isAll) {
                    ap = Apartacao_DAO.readAllLotesByFiltro(campo, condicao);
                } else {
                    ap = Apartacao_DAO.readAllApartacoes();
                }
                this.beginLock();

                signal.trigger(ap);

                this.endLock();
            }
        });
    }

}
