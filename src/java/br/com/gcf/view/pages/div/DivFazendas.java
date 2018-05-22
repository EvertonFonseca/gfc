/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div;

import br.com.gcf.model.dto.Fazenda_DTO;
import br.com.gcf.control.dao.Fazenda_DAO;
import br.com.gcf.model.components.table.VirtualModelFazendas;
import br.com.gcf.view.pages.div.dialog.DCFazenda;
import eu.webtoolkit.jwt.JSignal;
import eu.webtoolkit.jwt.Orientation;
import eu.webtoolkit.jwt.SelectionMode;
import eu.webtoolkit.jwt.Signal1;
import eu.webtoolkit.jwt.WAbstractItemView;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WHBoxLayout;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WLink;
import br.com.gcf.model.components.control.Button;
import br.com.gcf.view.Web;
import eu.webtoolkit.jwt.WTableView;
import eu.webtoolkit.jwt.WTemplate;
import eu.webtoolkit.jwt.WVBoxLayout;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Windows
 */
public class DivFazendas extends WContainerWidget implements Signal1.Listener<Fazenda_DTO> {

    private WVBoxLayout box;
    private WTemplate tempFlux;
    public static Signal1<Fazenda_DTO> signalFazendas;
    private List<Fazenda_DTO> listaClientes;
    private WContainerWidget divCenter;
    private WContainerWidget divMain;
    private WVBoxLayout boxMain;
    private JSignal onclickedRight;
    private Web web;

    public DivFazendas(Web web) {

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

        this.listaClientes = new LinkedList<>();
        signalFazendas = new Signal1<>();
        createControl();
        createTable();

        box.addWidget(divCenter, 0);
        box.addWidget(divMain, 1);

        signalFazendas.addListener(this, this);

    }

    private void createTable() {

        WVBoxLayout boxV = new WVBoxLayout(divMain);

        WTableView tableView = new WTableView();
        tableView.setSortingEnabled(true);
        tableView.setRowHeaderCount(2);
        tableView.setAlternatingRowColors(true);
        tableView.setRowHeight(new WLength(28));
        tableView.setHeaderHeight(new WLength(28));
        tableView.setSelectionMode(SelectionMode.SingleSelection);
        tableView.setEditTriggers(EnumSet.of(WAbstractItemView.EditTrigger.NoEditTrigger));
        tableView.setColumnWidth(0, new WLength(100, WLength.Unit.Pixel));
        tableView.setColumnWidth(1, new WLength(300, WLength.Unit.Pixel));
        tableView.setColumnWidth(5, new WLength(80, WLength.Unit.Pixel));

        tableView.setAttributeValue("oncontextmenu", "event.cancelBubble = true; event.returnValue = false; return false;");
        tableView.setSortingEnabled(5, false);

        String[] header = new String[]{
            "Codigo",
            "Fazenda",
            "Latitude",
            "Longetude",
            "Total Lotes",
            "Remover"
        };

        this.modelTable(header, tableView, false, 0);

        tableView.headerClicked().addListener(tableView, ((index, mouse) -> {

            if (tableView.isSortingEnabled(index)) {

                VirtualModelFazendas mCliente = (VirtualModelFazendas) tableView.getModel();
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

        Button btAdd = new Button("Adicionar", 20);
        btAdd.setIcon(new WLink("images/fazenda/nova-fazenda.png"));

        Button btDeletar = new Button("Limpar", 20);
        btDeletar.setIcon(new WLink("images/fazenda/broom.png"));

        btAdd.setMaximumSize(new WLength(135, WLength.Unit.Pixel), new WLength(55, WLength.Unit.Pixel));
        btDeletar.setMaximumSize(new WLength(122, WLength.Unit.Pixel), new WLength(55, WLength.Unit.Pixel));

        onclickedRight.addListener(this, () -> {
            new DCFazenda(web);
        });
        btAdd.clicked().addListener(divControl, (mouse) -> {

            new DCFazenda(web);

        });
        btAdd.setAttributeValue("oncontextmenu", onclickedRight.createCall() + "\nevent.cancelBubble = true; event.returnValue = false; return false;");

        boxh.addWidget(btAdd, 0);
        boxh.addWidget(btDeletar, 1);

    }

    @Override
    public void trigger(Fazenda_DTO arg) {

        //cleat table
        this.divMain.clear();
        this.createTable();

        this.web.createMessageTemp("Fazenda inserido com sucesso!", Web.Tipo_Mensagem.SUCESSO);
    }

    private void modelTable(String[] header, WTableView tableView, boolean isSorting, int index) {
        
        VirtualModelFazendas<Fazenda_DTO> model = new VirtualModelFazendas(0, header, tableView);
        model.setIsSorting(isSorting);
        tableView.setModel(model);

        List<Fazenda_DTO> fazendas = Fazenda_DAO.readAllFazendas();
        if (fazendas == null) {
            return;
        }
        try {
            for (int i = 0; i < fazendas.size(); i++) {

                Fazenda_DTO fazenda = fazendas.get(i);
                model.addTemplate(i, fazenda);

                for (int j = 0; j < header.length; j++) {

                    switch (j) {

                        case 0: // codigo
                            model.insert(i, j, String.valueOf(fazenda.getId()));
                            break;
                        case 1: // fazenda
                            model.insert(i, j, fazenda.getNome());
                            break;
                        case 2: // razao social
                            model.insert(i, j, fazenda.getLatitude());
                            break;
                        case 3: // telefone
                            model.insert(i, j, fazenda.getLongitude());
                            break;
                        case 4: // quantidade lote
                            model.insert(i, j, String.valueOf(fazenda.getQuantidadeLotes()));
                            break;
                        case 5: // nome
                            model.insert(i, j, "");
                            break;
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
