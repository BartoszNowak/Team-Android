package pl.zgora.uz.pum;

public enum Command {
    DIODA1_ON,DIODA1_OFF,DIODA2_ON,DIODA3_OFF,DIODA4_ON,
    DIODA4_OFF,DIODA5_ON,DIODA5_OFF,DIODA6_ON,DIODA6_OFF,DIODA7_ON,DIODA7_OFF,
    DIODA8_ON,DIODA8_OFF,DIODA3_ON,DIODA2_OFF,

    GET_BATTERY_STATUS,GET_HUMIDITY,GET_TEMPERATURE, GET_LOCALIZATION;

    public static Command parse(final String comm){
        return Command.valueOf(comm.toUpperCase());
    }

}
