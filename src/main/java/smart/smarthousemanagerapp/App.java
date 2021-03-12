package smart.smarthousemanagerapp;

import smart.state.Context;
import smart.state.SpeakerTabState;
import smart.state.LightTabState;
import smart.state.OutletTabState;
import smart.state.ThermastatTabState;
import smart.iterator.DeviceBtn;
import smart.iterator.BtnMenu;
import smart.factory.DeviceFactory;
import smart.command.Command;
import smart.command.DeviceOn;
import smart.command.DeviceOff;
import smart.command.Action;
import smart.command.OnOffCommand;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Iterator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import smart.factory.Device;
import smart.factory.Light;
import smart.factory.Outlet;
import smart.factory.Speaker;
import smart.factory.Thermastat;


/**
 * JavaFX App
 */
public class App extends Application {
    static ArrayList<Light> lights =new ArrayList<>();
    static ArrayList<Speaker>speakers = new ArrayList<>();
    static ArrayList<Thermastat> thermastats = new ArrayList<>();
    static ArrayList<Outlet> outlets = new ArrayList<>();
    
    static ToggleGroup toggleGroupLights = new ToggleGroup();
    static ToggleGroup toggleGroupSpeakers = new ToggleGroup();
    static ToggleGroup toggleGroupThermastats = new ToggleGroup();
    static ToggleGroup toggleGroupOutlets = new ToggleGroup();

    
    static HBox light_HBox = new HBox();
    static HBox speaker_HBox = new HBox();
    static HBox thermastat_HBox = new HBox();
    static HBox outlet_HBox = new HBox();

    
    static HBox light_control_HBox = new HBox();
    static Button btn_light_power = new Button("ON/OFF");
    static HBox speaker_control_HBox = new HBox();
    static Button btn_speaker_power = new Button("ON/OFF");
    static HBox thermastat_control_HBox = new HBox();
    static Button btn_thermastat_power = new Button("ON/OFF");
    static HBox outlet_control_HBox = new HBox();
    static Button btn_outlet_power = new Button("ON/OFF");
    
    static VBox light_status_VBox = new VBox();
    static VBox speaker_status_VBox = new VBox();
    static VBox thermastat_status_VBox = new VBox();
    static VBox outlet_status_VBox = new VBox();

    static ObservableList<DeviceObj> discoverableDevices = DiscoverableDevices();
    static TabPane tabPane = new TabPane();
    
    static Button btnAdd = new Button("Add device...");
    static HBox control_HBox = new HBox();
    
    static String tabState = "Lights";
    
    final static String onColor = "-fx-background-color: #00ff00; ";
    final static String offColor = "-fx-background-color: #ff0000;";

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Smart House Manager");
        Group group = new Group();
        Scene scene = new Scene(group, 400, 250);
        tabPane.setSide(Side.LEFT);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        BorderPane borderPane = new BorderPane();
        
        SplitPane hSplit = new SplitPane();
        hSplit.setOrientation(Orientation.HORIZONTAL);
        
        //Lights ****************************************************************************************
        Tab lightTab = new Tab();
        lightTab.setText("Lights");
        SplitPane light_split = new SplitPane();
        light_split.setOrientation(Orientation.VERTICAL);
        
        light_HBox.setAlignment(Pos.CENTER);
        btnAdd.setOnAction((ActionEvent e) -> {
            LaunchAddDeviceWindow(stage);
            
        });
        light_HBox.getChildren().add(btnAdd);
        
        //Lights CONTROL SECTION
        light_control_HBox.setAlignment(Pos.CENTER);

        // ON / OFF BUTTONS
        btn_light_power.setOnAction((ActionEvent e) -> {
            if (!lights.isEmpty()){
            boolean power= lights.get(toggleGroupLights.getToggles().indexOf((ToggleButton) toggleGroupLights.getSelectedToggle())).getOnOffState();
            CommandManager(power);
            }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Not connected to any device.");
            alert.showAndWait();
            }
        });
        
        SplitPane light_hSplit = new SplitPane();
        light_hSplit.setOrientation(Orientation.HORIZONTAL);
        light_status_VBox.setAlignment(Pos.CENTER);
        light_hSplit.getItems().addAll(light_status_VBox, light_control_HBox);
        light_split.getItems().addAll(light_HBox, light_hSplit);
        lightTab.setContent(light_split);
        tabPane.getTabs().add(lightTab);
        
        

        //Speakers ****************************************************************************************
        Tab speakerTab = new Tab();
        speakerTab.setText("Speakers");
        speaker_HBox.setAlignment(Pos.CENTER);
        SplitPane speaker_split = new SplitPane();
        speaker_split.setOrientation(Orientation.VERTICAL);

        //Speakers CONTROL SECTION
        speaker_control_HBox.setAlignment(Pos.CENTER);
        btn_speaker_power.setOnAction((ActionEvent e) -> {
            if (!speakers.isEmpty()){
            boolean power= speakers.get(toggleGroupSpeakers.getToggles().indexOf((ToggleButton) toggleGroupSpeakers.getSelectedToggle())).getOnOffState();
            CommandManager(power);
            }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Not connected to any device.");
            alert.showAndWait();
            }
        });
        
        SplitPane speaker_hSplit = new SplitPane();
        speaker_hSplit.setOrientation(Orientation.HORIZONTAL);
        speaker_status_VBox.setAlignment(Pos.CENTER);
        speaker_hSplit.getItems().addAll(speaker_status_VBox, speaker_control_HBox);
        speaker_split.getItems().addAll(speaker_HBox,speaker_hSplit);
        speakerTab.setContent(speaker_split);
        tabPane.getTabs().add(speakerTab);
        
        //Outlets ****************************************************************************************
        Tab outletTab = new Tab();
        outletTab.setText("Outlets");
        outlet_HBox.setAlignment(Pos.CENTER);
        SplitPane outlet_split = new SplitPane();
        outlet_split.setOrientation(Orientation.VERTICAL);

        //Outlet CONTROL SECTION
        outlet_control_HBox.setAlignment(Pos.CENTER);
        btn_outlet_power.setOnAction((ActionEvent e) -> {
            if (!outlets.isEmpty()){
            boolean power= outlets.get(toggleGroupOutlets.getToggles().indexOf((ToggleButton) toggleGroupOutlets.getSelectedToggle())).getOnOffState();
            CommandManager(power);
            }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Not connected to any device.");
            alert.showAndWait();
            }
        });
        
        SplitPane outlet_hSplit = new SplitPane();
        outlet_hSplit.setOrientation(Orientation.HORIZONTAL);
        outlet_status_VBox.setAlignment(Pos.CENTER);
        outlet_hSplit.getItems().addAll(outlet_status_VBox, outlet_control_HBox);
        outlet_split.getItems().addAll(outlet_HBox,outlet_hSplit);
        outletTab.setContent(outlet_split);
        tabPane.getTabs().add(outletTab);
        
        //Thermastat ****************************************************************************************
        Tab thermastatTab = new Tab();
        thermastatTab.setText("Thermastats");
        thermastat_HBox.setAlignment(Pos.CENTER);
        SplitPane thermastat_split = new SplitPane();
        thermastat_split.setOrientation(Orientation.VERTICAL);

        //Thermastat CONTROL SECTION
        thermastat_control_HBox.setAlignment(Pos.CENTER);
        btn_thermastat_power.setOnAction((ActionEvent e) -> {
            if (!thermastats.isEmpty()){
            boolean power= thermastats.get(toggleGroupThermastats.getToggles().indexOf((ToggleButton) toggleGroupThermastats.getSelectedToggle())).getOnOffState();
            CommandManager(power);
            }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Not connected to any device.");
            alert.showAndWait();
            }
        });
        
        SplitPane thermastat_hSplit = new SplitPane();
        thermastat_hSplit.setOrientation(Orientation.HORIZONTAL);
        thermastat_status_VBox.setAlignment(Pos.CENTER);
        thermastat_hSplit.getItems().addAll(thermastat_status_VBox, thermastat_control_HBox);
        thermastat_split.getItems().addAll(thermastat_HBox,thermastat_hSplit);
        thermastatTab.setContent(thermastat_split);
        tabPane.getTabs().add(thermastatTab);
        

        
        // TABPANE CHANGE
        tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab t, Tab t1) -> {
            Context context = new Context();
            switch (t1.getText()){
                case "Lights":
                    LightTabState lightTabState = new LightTabState();
                    lightTabState.doAction(context);
                    break;
                case "Speakers":
                    SpeakerTabState speakerTabState = new SpeakerTabState();
                    speakerTabState.doAction(context);
                    break;
                case "Outlets":
                    OutletTabState outletTabState = new OutletTabState();
                    outletTabState.doAction(context);
                    break;
                case "Thermastats":
                    ThermastatTabState thermastatTabState = new ThermastatTabState();
                    thermastatTabState.doAction(context); 
                   break;
            }
            tabState = context.getState().toString();
            DeviceIteratorManager();
        });
        
        
        // TOGGLE CHANGE
        toggleGroupLights.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle,Toggle toggle1) -> {
            UpdateStatusBox();
        });
                

        
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        group.getChildren().add(borderPane);
        
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
    
    // ******************************************************************************************************************************************************************
    //  LAUNCH ADD DEVICE WINDOW
    // ******************************************************************************************************************************************************************
    public void LaunchAddDeviceWindow(Stage stage){
        StackPane secondaryLayout = new StackPane();

        Scene secondScene = new Scene(secondaryLayout, 230, 200);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Add New Device");
        newWindow.setScene(secondScene);

        newWindow.setX(stage.getX() + 100);
        newWindow.setY(stage.getY() + 100);

        VBox vbox = new VBox();
        TableView<DeviceObj> tv_DeviceFinder = new TableView();
        tv_DeviceFinder.setItems(discoverableDevices);
        
        TableColumn deviceNameCol = new TableColumn("Name");
        deviceNameCol.setCellValueFactory( new PropertyValueFactory<DeviceObj, String>("name"));
        deviceNameCol.setMinWidth(100);
        TableColumn deviceTypeCol = new TableColumn("Type");
        deviceTypeCol.setCellValueFactory(new PropertyValueFactory<DeviceObj, String>("deviceType"));
        deviceTypeCol.setMinWidth(100);
        TableColumn deviceAddressCol = new TableColumn("Address");
        deviceAddressCol.setCellValueFactory(new PropertyValueFactory<DeviceObj, String>("deviceAddress"));
        deviceAddressCol.setVisible(false);
        TableColumn nickNameCol = new TableColumn("Nickname");
        nickNameCol.setCellValueFactory(new PropertyValueFactory<DeviceObj, String>("nickname"));
        nickNameCol.setVisible(false);
        TableColumn onOffCol = new TableColumn("On/Off");
        onOffCol.setCellValueFactory(new PropertyValueFactory<DeviceObj, String>("onOffState"));
        onOffCol.setVisible(false);
        tv_DeviceFinder.getColumns().setAll(deviceNameCol, deviceTypeCol, deviceAddressCol, nickNameCol, onOffCol);

        Label deviceNameLbl = new Label("Nickname:");  
        TextField deviceNameTf =new TextField();  
        Button btnAddSubmit = new Button("Submit");
        btnAddSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String name = tv_DeviceFinder.getSelectionModel().getSelectedItem().name;
                String type = tv_DeviceFinder.getSelectionModel().getSelectedItem().deviceType;
                String address = tv_DeviceFinder.getSelectionModel().getSelectedItem().deviceAddress;
                String nickname;
                if(!(deviceNameTf.getText().equals(""))){
                    nickname=deviceNameTf.getText();
                }else{
                    nickname=name;
                }
                discoverableDevices.remove(tv_DeviceFinder.getSelectionModel().getSelectedIndex());
                DeviceFactoryManager(name, type, address, nickname,false);
                newWindow.close();
                DeviceIteratorManager();
            }
        });
        vbox.getChildren().addAll(tv_DeviceFinder, deviceNameLbl, deviceNameTf,btnAddSubmit);
        secondaryLayout.getChildren().addAll(vbox);
        newWindow.show();
    }
    
    // ******************************************************************************************************************************************************************
    //  UPDATE STATUS BOX
    // ******************************************************************************************************************************************************************
    private void UpdateStatusBox(){
        ToggleButton selectedToggleButton = null;
        Label n, t, l;
        
        switch(tabState){
            case "Lights":
                light_status_VBox.getChildren().clear();
                selectedToggleButton = (ToggleButton) toggleGroupLights.getSelectedToggle();
                n = new Label ("Nickname: "+lights.get(toggleGroupLights.getToggles().indexOf(selectedToggleButton)).getNickname());
                t = new Label ("Device: "+lights.get(toggleGroupLights.getToggles().indexOf(selectedToggleButton)).getDeviceType());
                if (lights.get(toggleGroupLights.getToggles().indexOf(selectedToggleButton)).getOnOffState()){
                    l = new Label ("Status: On");
                }else{
                    l = new Label ("Status: Off");
                }
                light_status_VBox.getChildren().addAll(n,t,l);
                light_control_HBox.getChildren().clear();
                light_control_HBox.getChildren().addAll(btn_light_power);
                
                break;
            case "Speakers":
                speaker_status_VBox.getChildren().clear();
                selectedToggleButton = (ToggleButton) toggleGroupSpeakers.getSelectedToggle();
                n = new Label ("Nickname: "+speakers.get(toggleGroupSpeakers.getToggles().indexOf(selectedToggleButton)).getNickname());
                t = new Label ("Device: "+speakers.get(toggleGroupSpeakers.getToggles().indexOf(selectedToggleButton)).getDeviceType());
                if (speakers.get(toggleGroupSpeakers.getToggles().indexOf(selectedToggleButton)).getOnOffState()){
                    l = new Label ("Status: On");
                }else{
                    l = new Label ("Status: Off");
                }
                speaker_status_VBox.getChildren().addAll(n,t,l);
                speaker_control_HBox.getChildren().clear();
                speaker_control_HBox.getChildren().addAll(btn_speaker_power);
                break;
            case "Thermastats":
                thermastat_status_VBox.getChildren().clear();
                selectedToggleButton = (ToggleButton) toggleGroupThermastats.getSelectedToggle();
                n = new Label ("Nickname: "+thermastats.get(toggleGroupThermastats.getToggles().indexOf(selectedToggleButton)).getNickname());
                t = new Label ("Device: "+thermastats.get(toggleGroupThermastats.getToggles().indexOf(selectedToggleButton)).getDeviceType());
                if (thermastats.get(toggleGroupThermastats.getToggles().indexOf(selectedToggleButton)).getOnOffState()){
                    l = new Label ("Status: On");
                }else{
                    l = new Label ("Status: Off");
                }
                thermastat_status_VBox.getChildren().addAll(n,t,l);
                thermastat_control_HBox.getChildren().clear();
                thermastat_control_HBox.getChildren().addAll(btn_thermastat_power);           
                break;
            case "Outlets":
                outlet_status_VBox.getChildren().clear();
                selectedToggleButton = (ToggleButton) toggleGroupOutlets.getSelectedToggle();
                n = new Label ("Nickname: "+outlets.get(toggleGroupOutlets.getToggles().indexOf(selectedToggleButton)).getNickname());
                t = new Label ("Device: "+outlets.get(toggleGroupOutlets.getToggles().indexOf(selectedToggleButton)).getDeviceType());
                if (outlets.get(toggleGroupOutlets.getToggles().indexOf(selectedToggleButton)).getOnOffState()){
                    l = new Label ("Status: On");
                }else{
                    l = new Label ("Status: Off");
                }
                outlet_status_VBox.getChildren().addAll(n,t,l);
                outlet_control_HBox.getChildren().clear();
                outlet_control_HBox.getChildren().addAll(btn_outlet_power);    
                break;
        }
    }

    // ******************************************************************************************************************************************************************
    //  DEVICE FACTORY MANAGER
    // ******************************************************************************************************************************************************************
    private void DeviceFactoryManager(String deviceName, String selected, String deviceAddress, String nickname, boolean onOffState){
        Device device = DeviceFactory.getDevice(deviceName, selected, deviceAddress,nickname,onOffState);
        if (null != device.getDeviceType())switch (device.getDeviceType()) {
            case "Light":
                lights.add((Light)device);
                break;
            case "Speaker":
                speakers.add((Speaker)device);
                break;
            case "Thermastat":
                thermastats.add((Thermastat)device);
                break;
            case "Outlet":
                outlets.add((Outlet)device);
                break;
            default:
                break;
        }
    }
    
    // ******************************************************************************************************************************************************************
    //  TURN ON DEVICE
    // ******************************************************************************************************************************************************************
    public void TurnOnDevice(){
        ToggleButton selectedToggleButton = null;
        switch(tabState){
            case "Lights":
                selectedToggleButton = (ToggleButton) toggleGroupLights.getSelectedToggle();
                lights.get(toggleGroupLights.getToggles().indexOf(selectedToggleButton)).setOnOffState(true);
                break;
            case "Speakers":
                selectedToggleButton = (ToggleButton) toggleGroupSpeakers.getSelectedToggle();
                speakers.get(toggleGroupSpeakers.getToggles().indexOf(selectedToggleButton)).setOnOffState(true);
                break;
            case "Thermastats":
                selectedToggleButton = (ToggleButton) toggleGroupThermastats.getSelectedToggle();
                thermastats.get(toggleGroupThermastats.getToggles().indexOf(selectedToggleButton)).setOnOffState(true);
                break;
            case "Outlets":
                selectedToggleButton = (ToggleButton) toggleGroupOutlets.getSelectedToggle();
                outlets.get(toggleGroupOutlets.getToggles().indexOf(selectedToggleButton)).setOnOffState(true);
                break;
        }
        if (selectedToggleButton != null){
            selectedToggleButton.setStyle(onColor);
            UpdateStatusBox();
        }
    }
    
    public void CommandManager(boolean devicePower){
        if (!devicePower){
            OnOffCommand onOffCmd = new OnOffCommand();
            DeviceOn on = new DeviceOn(onOffCmd);
            Action action = new Action();
            action.takeCommand((Command) on);
            action.performCommand();
        }else{
            OnOffCommand onOffCmd = new OnOffCommand();
            DeviceOff off = new DeviceOff(onOffCmd);
            Action action = new Action();
            action.takeCommand((Command) off);
            action.performCommand();
        }
    }
    
    // ******************************************************************************************************************************************************************
    //  TURN OFF DEVICE
    // ******************************************************************************************************************************************************************
    public void TurnOffDevice(){
        ToggleButton selectedToggleButton = null;

        switch(tabState){
            case "Lights":
                selectedToggleButton = (ToggleButton) toggleGroupLights.getSelectedToggle();
                lights.get(toggleGroupLights.getToggles().indexOf(selectedToggleButton)).setOnOffState(false);
                break;
            case "Speakers":
                selectedToggleButton = (ToggleButton) toggleGroupSpeakers.getSelectedToggle();
                speakers.get(toggleGroupSpeakers.getToggles().indexOf(selectedToggleButton)).setOnOffState(false);
                break;
            case "Thermastats":
                selectedToggleButton = (ToggleButton) toggleGroupThermastats.getSelectedToggle();
                thermastats.get(toggleGroupThermastats.getToggles().indexOf(selectedToggleButton)).setOnOffState(false);
                break;
            case "Outlets":
                selectedToggleButton = (ToggleButton) toggleGroupOutlets.getSelectedToggle();
                outlets.get(toggleGroupOutlets.getToggles().indexOf(selectedToggleButton)).setOnOffState(false);
                break;
        }
        if (selectedToggleButton != null){
            selectedToggleButton.setStyle(offColor);
            UpdateStatusBox();
        }
    }
        
    // ******************************************************************************************************************************************************************
    //  DEVICE ITERATOR MANAGERR
    // ******************************************************************************************************************************************************************
    private void DeviceIteratorManager(){
        BtnMenu btnMenu = new BtnMenu();
        
        switch (tabState){
            case "Lights":
                light_HBox.getChildren().clear();
                toggleGroupLights=new ToggleGroup();
                lights.forEach(l -> {
                    btnMenu.addBtn(new DeviceBtn (l.getNickname(), l.getOnOffState()));
                });
                toggleGroupLights.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle,Toggle toggle1) -> {
                    if (toggle1 == null)
                        toggle.setSelected(true);
                    UpdateStatusBox();
                });
                break;
            case "Speakers":
                speaker_HBox.getChildren().clear();
                toggleGroupSpeakers=new ToggleGroup();
                speakers.forEach(s -> {
                    btnMenu.addBtn(new DeviceBtn (s.getNickname(), s.getOnOffState()));
                });
                toggleGroupSpeakers.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle,Toggle toggle1) -> {
                    if (toggle1 == null)
                        toggle.setSelected(true);
                    UpdateStatusBox();
                });
                break;
            case "Thermastats":
                thermastat_HBox.getChildren().clear();
                toggleGroupThermastats=new ToggleGroup();
                thermastats.forEach(t -> {
                    btnMenu.addBtn(new DeviceBtn (t.getNickname(), t.getOnOffState()));
                });
                toggleGroupThermastats.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle,Toggle toggle1) -> {
                    if (toggle1 == null)
                        toggle.setSelected(true);
                    UpdateStatusBox();
                });
                break;
            case "Outlets":
                outlet_HBox.getChildren().clear();
                toggleGroupOutlets=new ToggleGroup();
                outlets.forEach(o -> {
                    btnMenu.addBtn(new DeviceBtn (o.getNickname(), o.getOnOffState()));
                });
                toggleGroupOutlets.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle,Toggle toggle1) -> {
                    if (toggle1 == null)
                        toggle.setSelected(true);
                    UpdateStatusBox();
                });
                break;
        }
        
        Iterator<DeviceBtn> iterator = btnMenu.iterator();
        switch(tabState){
            case "Lights":
                while (iterator.hasNext()) {
                    DeviceBtn deviceBtn = iterator.next();
                    ToggleButton b = new ToggleButton(deviceBtn.toString());
                    b.setToggleGroup(toggleGroupLights);
                    if (deviceBtn.getOnOff()){
                        b.setStyle(onColor);
                    }else{
                        b.setStyle(offColor);
                    }
                light_HBox.getChildren().add(b);
                }
                light_HBox.getChildren().add(btnAdd); 
                break;
            case "Speakers":
                while (iterator.hasNext()) {
                    DeviceBtn deviceBtn = iterator.next();
                    ToggleButton b = new ToggleButton(deviceBtn.toString());
                    b.setToggleGroup(toggleGroupSpeakers);
                    if (deviceBtn.getOnOff()){
                        b.setStyle(onColor);
                    }else{
                        b.setStyle(offColor);
                    }
                speaker_HBox.getChildren().add(b);
                }
                speaker_HBox.getChildren().add(btnAdd);
                break;
            case "Thermastats":
                while (iterator.hasNext()) {
                    DeviceBtn deviceBtn = iterator.next();
                    ToggleButton b = new ToggleButton(deviceBtn.toString());
                    b.setToggleGroup(toggleGroupThermastats);
                    if (deviceBtn.getOnOff()){
                        b.setStyle(onColor);
                    }else{
                        b.setStyle(offColor);
                    }
                thermastat_HBox.getChildren().add(b);
                }
                thermastat_HBox.getChildren().add(btnAdd);
                break;
            case "Outlets":
                while (iterator.hasNext()) {
                    DeviceBtn deviceBtn = iterator.next();
                    ToggleButton b = new ToggleButton(deviceBtn.toString());
                    b.setToggleGroup(toggleGroupOutlets);
                    if (deviceBtn.getOnOff()){
                        b.setStyle(onColor);
                    }else{
                        b.setStyle(offColor);
                    }
                outlet_HBox.getChildren().add(b);
                }
                outlet_HBox.getChildren().add(btnAdd);
                break;
        }
        SelectToggleButton();
    }
    
    public void SelectToggleButton(){
        switch (tabState){
            case "Lights":
                if (!toggleGroupLights.getToggles().isEmpty())
                    toggleGroupLights.getToggles().get(0).setSelected(true);
                break;
            case "Speakers":
                if (!toggleGroupSpeakers.getToggles().isEmpty())
                    toggleGroupSpeakers.getToggles().get(0).setSelected(true);
                break;
            case "Thermastats":
                if (!toggleGroupThermastats.getToggles().isEmpty())
                    toggleGroupThermastats.getToggles().get(0).setSelected(true);
                break;
            case "Outlets":
                if (!toggleGroupOutlets.getToggles().isEmpty())
                    toggleGroupOutlets.getToggles().get(0).setSelected(true);
                break;
        }
    }
    
    // ******************************************************************************************************************************************************************
    //  DISCOVERABLE DEVICES - Sample devices for app
    // ******************************************************************************************************************************************************************
    private static ObservableList DiscoverableDevices(){
            ObservableList list = FXCollections.observableArrayList(
            new DeviceObj("SmartBulb20","Light","7dhqk2g3d8q", null, false, false),
            new DeviceObj("Bluetooth Speaker","Speaker","8vndh9dlwo", null, false, false),
            new DeviceObj("Sungsam Smart Outlet","Outlet","lm79dh39s", null, false, false),
            new DeviceObj("SmartStat","Thermastat","gg67dmo12s", null, false, false),
            new DeviceObj("SmartBulb20","Light","86471hdha92", null, false, false)
        );
            return list;
    }

}