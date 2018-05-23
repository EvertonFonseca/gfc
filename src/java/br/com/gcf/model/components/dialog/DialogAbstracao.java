/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.dialog;

import eu.webtoolkit.jwt.Cursor;
import eu.webtoolkit.jwt.PositionScheme;
import eu.webtoolkit.jwt.Side;
import eu.webtoolkit.jwt.Signal;
import eu.webtoolkit.jwt.Signal1;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WDialog;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WText;

/**
 *
 * @author Windows
 */
public abstract class DialogAbstracao extends WDialog {

    private Signal signalClose;
    private Signal1<String> signalInsert;

    public DialogAbstracao() {

        this.signalClose = new Signal(this);
        this.signalInsert = new Signal1(this);

        WContainerWidget titleDiv = getTitleBar();
        titleDiv.setPositionScheme(PositionScheme.Relative);

        WText btClose = new WText("<span class=\"lnr lnr-cross\"></span>");
        btClose.mouseWentOver().addListener(btClose, () -> {
            btClose.getDecorationStyle().setCursor(Cursor.PointingHandCursor);
        });
        btClose.mouseWentOut().addListener(btClose, () -> {
            btClose.getDecorationStyle().setCursor(Cursor.ArrowCursor);
        });
        btClose.clicked().addListener(btClose, (arg) -> {

            this.reject();
        });
        btClose.setPositionScheme(PositionScheme.Relative);
        btClose.setOffsets(-40, Side.Top);
        btClose.resize(new WLength(25, WLength.Unit.Pixel), new WLength(25, WLength.Unit.Pixel));
        btClose.setMinimumSize(new WLength(25, WLength.Unit.Pixel), new WLength(25, WLength.Unit.Pixel));
        btClose.setMaximumSize(new WLength(25, WLength.Unit.Pixel), new WLength(25, WLength.Unit.Pixel));
        btClose.setFloatSide(Side.Right);

        titleDiv.addWidget(btClose);
    }

    public DialogAbstracao(String titule) {
        super(titule);
        this.signalClose = new Signal(this);
        this.signalInsert = new Signal1(this);

        WContainerWidget titleDiv = getTitleBar();
        titleDiv.setPositionScheme(PositionScheme.Relative);

        WText btClose = new WText("<span class=\"lnr lnr-cross\"></span>");
        btClose.mouseWentOver().addListener(btClose, () -> {
            btClose.getDecorationStyle().setCursor(Cursor.PointingHandCursor);
        });
        btClose.mouseWentOut().addListener(btClose, () -> {
            btClose.getDecorationStyle().setCursor(Cursor.ArrowCursor);
        });
        btClose.clicked().addListener(btClose, (arg) -> {

            this.reject();
        });
        btClose.setPositionScheme(PositionScheme.Relative);
        btClose.setOffsets(-40, Side.Top);
        btClose.resize(new WLength(25, WLength.Unit.Pixel), new WLength(25, WLength.Unit.Pixel));
        btClose.setMinimumSize(new WLength(25, WLength.Unit.Pixel), new WLength(25, WLength.Unit.Pixel));
        btClose.setMaximumSize(new WLength(25, WLength.Unit.Pixel), new WLength(25, WLength.Unit.Pixel));
        btClose.setFloatSide(Side.Right);

        titleDiv.addWidget(btClose);
    }

    @Override
    public void remove() {

        this.getSignalClose().trigger();
        super.remove(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reject() {
        this.getSignalClose().trigger();
        super.reject(); //To change body of generated methods, choose Tools | Templates.
        super.remove();
    }

    /**
     * @return the signalClose
     */
    public Signal getSignalClose() {
        return signalClose;
    }

    /**
     * @return the signalInsert
     */
    public Signal1<String> getSignalInsert() {
        return signalInsert;
    }
    
    
}
