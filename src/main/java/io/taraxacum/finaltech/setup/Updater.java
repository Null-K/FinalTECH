package io.taraxacum.finaltech.setup;

import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.plugin.dto.ConfigFileManager;
import io.taraxacum.libs.plugin.dto.LanguageManager;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

public class Updater implements Consumer<FinalTech>{
    private final Map<String, UpdateFunction> versionMap = new HashMap<>();
    private boolean init = false;
    private static volatile Updater instance;
    public static final String LATEST_VERSION = "20230211";

    public void init() {
        LanguageManager languageManager = FinalTech.getLanguageManager();
        ConfigFileManager configManager = FinalTech.getConfigManager();
        Logger logger = FinalTech.logger();
        Boolean updateLanguage = configManager.getOrDefault(true, "update", "language");
        if(updateLanguage) {
            logger.info("You have enabled the config updater for language file");
        }

        versionMap.put("20220811", new UpdateFunction() {
            @Override
            protected String targetVersion() {
                return "20221204";
            }

            @Override
            protected void update(FinalTech finalTech) {
                if(updateLanguage) {
                    List<String> lore1 = new ArrayList<>();
                    lore1.add("{color:normal}Input items to get {id:FINALTECH_UNORDERED_DUST} {color:normal}or {id:FINALTECH_ORDERED_DUST}");
                    lore1.add("");
                    lore1.add("{color:normal}A pair of random number will be generated.");
                    lore1.add("{color:normal}They will be used to determine how to craft item.");

                    List<String> lore2 = new ArrayList<>();
                    lore2.add("{color:normal}Input at least the specified amount of items");
                    lore2.add("{color:normal}Input at least the specified quantity of different type of items");
                    lore2.add("{color:normal}Then it will generate one {id:FINALTECH_UNORDERED_DUST}");

                    List<String> lore3 = new ArrayList<>();
                    lore3.add("{color:normal}Input just the specified amount of items");
                    lore3.add("{color:normal}Input just the specified quantity of different type of items");
                    lore3.add("{color:normal}Then it will generate one {id:FINALTECH_ORDERED_DUST}");

                    languageManager.setValue(lore1, "items", "FINALTECH_ORDERED_DUST_FACTORY_DIRT", "info", "1", "lore");
                    languageManager.setValue(lore2, "items", "FINALTECH_ORDERED_DUST_FACTORY_DIRT", "info", "2", "lore");
                    languageManager.setValue(lore3, "items", "FINALTECH_ORDERED_DUST_FACTORY_DIRT", "info", "3", "lore");
                }
            }
        });

        versionMap.put("20221204", new UpdateFunction() {
            @Override
            protected String targetVersion() {
                return "20221229";
            }

            @Override
            protected void update(FinalTech finalTech) {
                List<String> allowedIdList = new ArrayList<>();
                allowedIdList.add("FINALTECH_ADVANCED_COMPOSTER");
                allowedIdList.add("FINALTECH_ADVANCED_JUICER");
                allowedIdList.add("FINALTECH_ADVANCED_FURNACE");
                allowedIdList.add("FINALTECH_ADVANCED_GOLD_PAN");
                allowedIdList.add("FINALTECH_ADVANCED_DUST_WASHER");
                allowedIdList.add("FINALTECH_ADVANCED_INGOT_FACTORY");
                allowedIdList.add("FINALTECH_ADVANCED_CRUCIBLE");
                allowedIdList.add("FINALTECH_ADVANCED_ORE_GRINDER");
                allowedIdList.add("FINALTECH_ADVANCED_HEATED_PRESSURE_CHAMBER");
                allowedIdList.add("FINALTECH_ADVANCED_INGOT_PULVERIZER");
                allowedIdList.add("FINALTECH_ADVANCED_AUTO_DRIER");
                allowedIdList.add("FINALTECH_ADVANCED_PRESS");
                allowedIdList.add("FINALTECH_ADVANCED_FOOD_FACTORY");
                allowedIdList.add("FINALTECH_ADVANCED_FREEZER");
                allowedIdList.add("FINALTECH_ADVANCED_CARBON_PRESS");
                allowedIdList.add("FINALTECH_ADVANCED_SMELTERY");
                allowedIdList.add("FINALTECH_ADVANCED_DUST_FACTORY");
                configManager.setValue(allowedIdList, "FINALTECH_MULTI_FRAME_MACHINE", "allowed-id");

                if(updateLanguage) {
                    languageManager.setValue("{color:prandom}Remote Accessor {color:conceal}- {color:positive}Consumable", "items", "FINALTECH_CONSUMABLE_REMOTE_ACCESSOR", "name");
                    languageManager.setValue("{color:prandom}Remote Accessor {color:conceal}- {color:positive}Configurable", "items", "FINALTECH_CONFIGURABLE_REMOTE_ACCESSOR", "name");
                    languageManager.setValue("{color:prandom}Expanded Remote Accessor {color:conceal}- {color:positive}Consumable", "items", "FINALTECH_EXPANDED_CONSUMABLE_REMOTE_ACCESSOR", "name");
                    languageManager.setValue("{color:prandom}Expanded Remote Accessor {color:conceal}- {color:positive}Configurable", "items", "FINALTECH_EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR", "name");
                    languageManager.setValue("{color:prandom}Transporter", "items", "FINALTECH_TRANSPORTER", "name");
                    languageManager.setValue("{color:prandom}Transporter {color:conceal}- {color:positive}Consumable", "items", "FINALTECH_CONSUMABLE_TRANSPORTER", "name");
                    languageManager.setValue("{color:prandom}Transporter {color:conceal}- {color:positive}Configurable", "items", "FINALTECH_CONFIGURABLE_TRANSPORTER", "name");
                    languageManager.setValue("{color:prandom}Expanded Transporter {color:conceal}- {color:positive}Consumable", "items", "FINALTECH_EXPANDED_CONSUMABLE_TRANSPORTER", "name");
                    languageManager.setValue("{color:prandom}Expanded Transporter {color:conceal}- {color:positive}Configurable", "items", "FINALTECH_EXPANDED_CONFIGURABLE_TRANSPORTER", "name");

                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_EXPANDED_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_TRANSPORTER", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONSUMABLE_TRANSPORTER", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONFIGURABLE_TRANSPORTER", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_EXPANDED_CONSUMABLE_TRANSPORTER", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_EXPANDED_CONFIGURABLE_TRANSPORTER", "info", "1", "name");

                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_EXPANDED_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_CONSUMABLE_TRANSPORTER", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_CONFIGURABLE_TRANSPORTER", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_EXPANDED_CONSUMABLE_TRANSPORTER", "info", "1", "output");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_EXPANDED_CONFIGURABLE_TRANSPORTER", "info", "1", "output");

                    List<String> lore1 = new ArrayList<>();
                    lore1.add("{color:normal}Right click it to config it");
                    lore1.add("{color:normal}Right click it to open slimefun machine it looking at");
                    languageManager.setValue(lore1, "items", "FINALTECH_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "lore");
                    languageManager.setValue(lore1, "items", "FINALTECH_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "lore");
                    languageManager.setValue(lore1, "items", "FINALTECH_EXPANDED_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "lore");
                    languageManager.setValue(lore1, "items", "FINALTECH_EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "lore");

                    List<String> lore2 = new ArrayList<>();
                    lore2.add("{color:normal}Right click it to teleport to the target place");
                    lore2.add("{color:normal}Range: {color:number}{1}");
                    languageManager.setValue(lore2, "items", "FINALTECH_TRANSPORTER", "info", "1", "lore");

                    List<String> lore3 = new ArrayList<>();
                    lore3.add("{color:normal}Right click it to config it");
                    lore3.add("{color:normal}Right click it to teleport to the target place");

                    languageManager.setValue(lore3, "items", "FINALTECH_CONSUMABLE_TRANSPORTER", "info", "1", "lore");
                    languageManager.setValue(lore3, "items", "FINALTECH_CONFIGURABLE_TRANSPORTER", "info", "1", "lore");
                    languageManager.setValue(lore3, "items", "FINALTECH_EXPANDED_CONSUMABLE_TRANSPORTER", "info", "1", "lore");
                    languageManager.setValue(lore3, "items", "FINALTECH_EXPANDED_CONFIGURABLE_TRANSPORTER", "info", "1", "lore");

                    languageManager.setValue("{color:prandom}Time Generator", "items", "FINALTECH_TIME_GENERATOR", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_TIME_GENERATOR", "info", "1", "name");

                    languageManager.setValue("{color:prandom}Time Capacitor", "items", "FINALTECH_TIME_CAPACITOR", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_TIME_CAPACITOR", "info", "1", "name");

                    List<String> lore4 = new ArrayList<>();
                    lore4.add("{color:normal}Generate energy 1J per {color:number}{1}s");
                    lore4.add("{color:normal}Can store {color:number}{2} J {color:normal}of energy");
                    lore4.add("{color:normal}Double stored energy with time going");
                    languageManager.setValue(lore4, "items", "FINALTECH_TIME_GENERATOR", "info", "1", "lore");

                    List<String> lore5 = new ArrayList<>();
                    lore5.add("{color:normal}Can store {color:number}{1} J {color:normal}of energy");
                    lore5.add("{color:normal}Double stored energy with time going");
                    languageManager.setValue(lore5, "items", "FINALTECH_TIME_CAPACITOR", "info", "1", "lore");

                    List<String> lore6 = new ArrayList<>();
                    lore6.add("{color:normal}Stored Energy: {color:number}{1} J");
                    languageManager.setValue(lore6, "items", "FINALTECH_TIME_GENERATOR", "status", "lore");
                    languageManager.setValue(lore6, "items", "FINALTECH_TIME_CAPACITOR", "status", "lore");

                    languageManager.setValue("{color:prandom}Multi Frame Machine", "items", "FINALTECH_MULTI_FRAME_MACHINE", "name");

                    languageManager.setValue("{color:random}Disc", "categories", "FINALTECH_MAIN_MENU_DISC", "name");
                    languageManager.setValue("{color:random}Final Item", "categories", "FINALTECH_SUB_MENU_FINAL_ITEM", "name");
                    languageManager.setValue("{color:random}Trophy", "categories", "FINALTECH_SUB_MENU_TROPHY", "name");
                    languageManager.setValue("{color:random}Deprecated", "categories", "FINALTECH_SUB_MENU_DEPRECATED", "name");

                    languageManager.setValue("{color:prandom}Digital Extraction", "items", "FINALTECH_DIGITAL_EXTRACTION", "name");

                    List<String> lore7 = new ArrayList<>();
                    lore7.add("{color:normal}Can store {color:number}{1} J {color:normal}of energy every stack");
                    lore7.add("{color:normal}Can store {color:number}{2} stack {color:normal}of energy");
                    lore7.add("{color:normal}Generate energy with the same amount of stored energy stack every {color:number}{3} s");

                    languageManager.setValue(lore7, "items", "FINALTECH_SMALL_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_MEDIUM_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_BIG_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_LARGE_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_CARBONADO_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_ENERGIZED_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_ENERGIZED_STACK_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_OVERLOADED_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "FINALTECH_MATRIX_EXPANDED_CAPACITOR", "info", "1", "lore");

                    languageManager.setValue("", "items", "FINALTECH_NORMAL_ELECTRICITY_SHOOT_PILE", "info", "2");
                    languageManager.setValue("", "items", "FINALTECH_ENERGIZED_ELECTRICITY_SHOOT_PILE", "info", "2");
                    languageManager.setValue("", "items", "FINALTECH_OVERLOADED_ELECTRICITY_SHOOT_PILE", "info", "2");

                    List<String> lore8 = new ArrayList<>();
                    lore8.add("{color:normal}Transfer energy stored in the capacitor or generator behind it");
                    lore8.add("{color:normal}To the machine it is pointing at");
                    lore8.add("");
                    lore8.add("{color:normal}It will charge only one machine at same time");

                    List<String> lore9 = new ArrayList<>();
                    lore9.add("{color:normal}Stored Energy: {color:number}{1} J");
                    lore9.add("{color:normal}Transfer Energy: {color:number}{2} J");

                    languageManager.setValue("{color:prandom}Normal Electricity Shoot Pile {color:conceal}- {color:positive}Consumable", "items", "FINALTECH_NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE", "info", "1", "name");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE", "info", "1", "output");
                    languageManager.setValue(lore8, "items", "FINALTECH_NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE", "info", "1", "lore");
                    languageManager.setValue(lore9, "items", "FINALTECH_NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE", "status", "lore");

                    languageManager.setValue("{color:prandom}Normal Electricity Shoot Pile {color:conceal}- {color:positive}Configurable", "items", "FINALTECH_NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE", "info", "1", "name");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "items", "FINALTECH_NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE", "info", "1", "output");
                    languageManager.setValue(lore8, "items", "FINALTECH_NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE", "info", "1", "lore");
                    languageManager.setValue(lore9, "items", "FINALTECH_NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE", "status", "lore");

                    languageManager.setValue("{color:prandom}Gravity Reversal Rune", "items", "FINALTECH_GRAVITY_REVERSAL_RUNE", "name");
                    languageManager.setValue("{color:passive}Usage", "items", "FINALTECH_GRAVITY_REVERSAL_RUNE", "info", "1", "name");
                    List<String> lore10 = new ArrayList<>();
                    lore10.add("{color:action}[Right-Click] {color:normal}Reverse your vertical speed");
                    languageManager.setValue(lore10, "items", "FINALTECH_GRAVITY_REVERSAL_RUNE", "info", "1", "lore");
                }
            }
        });

        versionMap.put("20221229", new UpdateFunction() {
            @Override
            protected String targetVersion() {
                return "20230109";
            }

            @Override
            protected void update(FinalTech finalTech) {
                ConfigFileManager itemManager = FinalTech.getItemManager();
                List<String> l = new ArrayList<>();
                l.add("CARGO_NODE_INPUT");
                l.add("CARGO_NODE_OUTPUT");
                l.add("CARGO_NODE_OUTPUT_ADVANCED");
                itemManager.setValue(l, "MACHINE_CONFIGURATOR", "item-config", "cargo-node");

                l = new ArrayList<>();
                l.add("FINALTECH_ADVANCED_COMPOSTER");
                l.add("FINALTECH_ADVANCED_JUICER");
                l.add("FINALTECH_ADVANCED_FURNACE");
                l.add("FINALTECH_ADVANCED_GOLD_PAN");
                l.add("FINALTECH_ADVANCED_DUST_WASHER");
                l.add("FINALTECH_ADVANCED_INGOT_FACTORY");
                l.add("FINALTECH_ADVANCED_CRUCIBLE");
                l.add("FINALTECH_ADVANCED_ORE_GRINDER");
                l.add("FINALTECH_ADVANCED_HEATED_PRESSURE_CHAMBER");
                l.add("FINALTECH_ADVANCED_INGOT_PULVERIZER");
                l.add("FINALTECH_ADVANCED_AUTO_DRIER");
                l.add("FINALTECH_ADVANCED_PRESS");
                l.add("FINALTECH_ADVANCED_FOOD_FACTORY");
                l.add("FINALTECH_ADVANCED_FREEZER");
                l.add("FINALTECH_ADVANCED_CARBON_PRESS");
                l.add("FINALTECH_ADVANCED_SMELTERY");
                l.add("FINALTECH_ADVANCED_DUST_FACTORY");
                l.add("FINALTECH_ADVANCED_COMPOSTER");
                l.add("FINALTECH_ADVANCED_COMPOSTER");
                itemManager.setValue(l, "MACHINE_CONFIGURATOR", "item-config", "advanced-machine");

                l = new ArrayList<>();
                l.add("FINALTECH_POINT_TRANSFER");
                l.add("FINALTECH_ADVANCED_POINT_TRANSFER");
                itemManager.setValue(l, "MACHINE_CONFIGURATOR", "item-config", "ft-cargo-point");


                l = new ArrayList<>();
                l.add("FINALTECH_MESH_TRANSFER");
                l.add("FINALTECH_ADVANCED_MESH_TRANSFER");
                itemManager.setValue(l, "MACHINE_CONFIGURATOR", "item-config", "ft-cargo-mesh");


                l = new ArrayList<>();
                l.add("FINALTECH_LINE_TRANSFER");
                l.add("FINALTECH_ADVANCED_LINE_TRANSFER");
                itemManager.setValue(l, "MACHINE_CONFIGURATOR", "item-config", "ft-cargo-line");


                l = new ArrayList<>();
                l.add("FINALTECH_LOCATION_TRANSFER");
                l.add("FINALTECH_ADVANCED_LOCATION_TRANSFER");
                itemManager.setValue(l, "MACHINE_CONFIGURATOR", "item-config", "ft-cargo-location");

                // FINALTECH_AUTO_ITEM_DISMANTLE_TABLE

                l = new ArrayList<>();
                l.add("enhanced_crafting_table");
                l.add("grind_stone");
                l.add("armor_forge");
                l.add("ore_crusher");
                l.add("compressor");
                l.add("smeltery");
                l.add("pressure_chamber");
                l.add("magic_workbench");
                l.add("gold_pan");
                l.add("juicer");
                l.add("ancient_altar");
                l.add("heated_pressure_chamber");
                l.add("food_fabricator");
                l.add("food_composter");
                l.add("freezer");
                itemManager.setValue(l, "FINALTECH_AUTO_ITEM_DISMANTLE_TABLE", "allowed-recipe-type");

                l = new ArrayList<>();
                l.add("GOLD_DUST");
                l.add("IRON_DUST");
                l.add("COMPRESSED_COBBLESTONE_1");
                l.add("COMPRESSED_COBBLESTONE_2");
                l.add("COMPRESSED_COBBLESTONE_3");
                l.add("COMPRESSED_COBBLESTONE_4");
                l.add("COMPRESSED_COBBLESTONE_5");
                itemManager.setValue(l, "FINALTECH_AUTO_ITEM_DISMANTLE_TABLE", "not-allowed-id");

                // config tweak

                configManager.setValue(128, "tweak", "range-limit", "FINALTECH_ITEM_DISMANTLE_TABLE", "range");
                configManager.setValue(4, "tweak", "range-limit", "FINALTECH_ITEM_DISMANTLE_TABLE", "mul-range");
                configManager.setValue(true, "tweak", "range-limit", "FINALTECH_ITEM_DISMANTLE_TABLE", "drop-self");
                configManager.setValue("{1} §4is not allowed to be placed too closely", "tweak", "range-limit", "FINALTECH_ITEM_DISMANTLE_TABLE", "message");

                configManager.setValue(128, "tweak", "range-limit", "FINALTECH_ORDERED_DUST_FACTORY_DIRT", "range");
                configManager.setValue(4, "tweak", "range-limit", "FINALTECH_ORDERED_DUST_FACTORY_DIRT", "mul-range");
                configManager.setValue(false, "tweak", "range-limit", "FINALTECH_ORDERED_DUST_FACTORY_DIRT", "drop-self");
                configManager.setValue("{1} §4is not allowed to be placed too closely", "tweak", "range-limit", "FINALTECH_ORDERED_DUST_FACTORY_DIRT", "message");

                configManager.setValue(128, "tweak", "range-limit", "FINALTECH_ORDERED_DUST_FACTORY_STONE", "range");
                configManager.setValue(4, "tweak", "range-limit", "FINALTECH_ORDERED_DUST_FACTORY_STONE", "mul-range");
                configManager.setValue(false, "tweak", "range-limit", "FINALTECH_ORDERED_DUST_FACTORY_STONE", "drop-self");
                configManager.setValue("{1} §4is not allowed to be placed too closely", "tweak", "range-limit", "FINALTECH_ORDERED_DUST_FACTORY_STONE", "message");

                l = new ArrayList<>();
                l.add("FINALTECH_ORDERED_DUST_FACTORY_DIRT");
                l.add("FINALTECH_ORDERED_DUST_FACTORY_STONE");
                configManager.setValue(l, "tweak", "performance-limit", "item");

                l = new ArrayList<>();
                l.add("VARIABLE_WIRE_RESISTANCE");
                l.add("VARIABLE_WIRE_CAPACITOR");
                l.add("ENTROPY_SEED");
                l.add("EQUIVALENT_CONCEPT");
                l.add("MATRIX_GENERATOR");
                l.add("MATRIX_REACTOR");
                configManager.setValue(l, "tweak", "anti-accelerate", "item");

                configManager.setValue(1, "tweak", "general", "FINALTECH_FUEL_OPERATOR");
                configManager.setValue(2, "tweak", "independent", "FINALTECH_BASIC_LOGIC_FACTORY");

                if(updateLanguage) {
                    // HELPER: FORCE_CLOSE
                    languageManager.setValue("{color:normal}Force Close {color:conceal}- {color:random}False", "helper", "FORCE_CLOSE", "false", "name");
                    List<String> lore = new ArrayList<>();
                    lore.add("{color:normal}Not close menu if not work");
                    languageManager.setValue(lore, "helper", "FORCE_CLOSE", "false", "lore");

                    languageManager.setValue("{color:normal}Force Close {color:conceal}- {color:random}True", "helper", "FORCE_CLOSE", "true", "name");
                    List<String> lore0 = new ArrayList<>();
                    lore0.add("{color:normal}Close menu if not work");
                    languageManager.setValue(lore0, "helper", "FORCE_CLOSE", "true", "lore");

                    // FINALTECH_CONFIGURATION_COPIER

                    languageManager.setValue("{color:prandom}Configuration Copier", "items", "FINALTECH_CONFIGURATION_COPIER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONFIGURATION_COPIER", "info", "1", "name");
                    List<String> lore1 = new ArrayList<>();
                    lore1.add("{color:normal}Copy the configuration of machine it looks at");
                    languageManager.setValue(lore1, "items", "FINALTECH_CONFIGURATION_COPIER", "info", "1", "lore");
                    languageManager.setValue("FINALTECH_MACHINE_CONFIGURATOR", "FINALTECH_CONFIGURATION_COPIER", "info", "1", "output");

                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONFIGURATION_COPIER", "info", "2", "name");
                    List<String> lore2 = new ArrayList<>();
                    lore2.add("{color:normal}Use digital item to specify the location");
                    lore2.add("{color:normal}Optional, default 0");
                    languageManager.setValue(lore2, "items", "FINALTECH_CONFIGURATION_COPIER", "info", "2", "lore");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "FINALTECH_CONFIGURATION_COPIER", "info", "2", "output");

                    // FINALTECH_CONFIGURATION_PASTER

                    languageManager.setValue("{color:prandom}Configuration Paster", "items", "FINALTECH_CONFIGURATION_PASTER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONFIGURATION_PASTER", "info", "1", "name");
                    List<String> lore3 = new ArrayList<>();
                    lore3.add("{color:normal}Copy the configuration of machine it looks at");
                    languageManager.setValue(lore3, "items", "FINALTECH_CONFIGURATION_PASTER", "info", "1", "lore");
                    languageManager.setValue("FINALTECH_MACHINE_CONFIGURATOR", "FINALTECH_CONFIGURATION_PASTER", "info", "1", "output");

                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONFIGURATION_PASTER", "info", "2", "name");
                    List<String> lore4 = new ArrayList<>();
                    lore4.add("{color:normal}Use digital item to specify the location");
                    lore4.add("{color:normal}Optional, default 0");
                    languageManager.setValue(lore4, "items", "FINALTECH_CONFIGURATION_PASTER", "info", "2", "lore");
                    languageManager.setValue("FINALTECH_DIGITAL_ZERO", "FINALTECH_CONFIGURATION_PASTER", "info", "2", "output");

                    // FINALTECH_CLICK_WORK_MACHINE

                    languageManager.setValue("{color:prandom}Click Work Machine", "items", "FINALTECH_CLICK_WORK_MACHINE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CLICK_WORK_MACHINE", "info", "1", "name");
                    List<String> lore5 = new ArrayList<>();
                    lore5.add("{color:normal}Transfer item from input slot to output slot while being clicked");
                    languageManager.setValue(lore5, "items", "FINALTECH_CLICK_WORK_MACHINE", "info", "1", "lore");
                    languageManager.setValue("FINALTECH_MACHINE_CONFIGURATOR", "FINALTECH_CLICK_WORK_MACHINE", "info", "1", "output");

                    // FINALTECH_SIMULATE_CLICK_MACHINE

                    languageManager.setValue("{color:prandom}Simulate Click Machine", "items", "FINALTECH_SIMULATE_CLICK_MACHINE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_SIMULATE_CLICK_MACHINE", "info", "1", "name");
                    List<String> lore6 = new ArrayList<>();
                    lore6.add("{color:normal}Transfer item from input slot to output slot");
                    lore6.add("{color:normal}While there is a digit item in input slot");
                    lore6.add("{color:normal}And the machine in it's bottom will be clicked by around player");
                    lore6.add("{color:normal}Digit to range rate: {color:number}{1}");
                    languageManager.setValue(lore6, "items", "FINALTECH_SIMULATE_CLICK_MACHINE", "info", "1", "lore");
                    languageManager.setValue("FINALTECH_DIGITAL_ONE", "FINALTECH_SIMULATE_CLICK_MACHINE", "info", "1", "output");

                    // FINALTECH_CONSUMABLE_SIMULATE_CLICK_MACHINE

                    languageManager.setValue("{color:prandom}Simulate Click Machine {color:conceal}- {color:positive}Consumable", "items", "FINALTECH_CONSUMABLE_SIMULATE_CLICK_MACHINE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_CONSUMABLE_SIMULATE_CLICK_MACHINE", "info", "1", "name");
                    List<String> lore7 = new ArrayList<>();
                    lore7.add("{color:normal}Consume digit item");
                    lore7.add("{color:normal}Then the machine in it's bottom will be clicked by around player");
                    lore7.add("{color:normal}Digit to range rate: {color:number}{1}");
                    languageManager.setValue(lore7, "items", "FINALTECH_CONSUMABLE_SIMULATE_CLICK_MACHINE", "info", "1", "lore");
                    languageManager.setValue("FINALTECH_DIGITAL_ONE", "FINALTECH_CONSUMABLE_SIMULATE_CLICK_MACHINE", "info", "1", "output");

                    // FINALTECH_ITEM_DISMANTLE_TABLE

                    List<String> lore8 = new ArrayList<>();
                    lore8.add("{color:normal}Charge: {color:number}{1} {color:conceal}/ {color:number}{2}");
                    languageManager.setValue(lore8, "items", "FINALTECH_ITEM_DISMANTLE_TABLE", "status-icon", "lore");

                    // FINALTECH_AUTO_ITEM_DISMANTLE_TABLE

                    languageManager.setValue("{color:prandom}Auto Item Dismantle Table", "items", "FINALTECH_AUTO_ITEM_DISMANTLE_TABLE", "name");

                    // FINALTECH_ADVANCED_POINT_TRANSFER

                    languageManager.setValue("{color:prandom}Advanced Point Transfer", "items", "FINALTECH_ADVANCED_POINT_TRANSFER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_ADVANCED_POINT_TRANSFER", "info", "1", "name");
                    languageManager.setValue(List.of("{color:normal}Transfer items from one place to another place"), "items", "FINALTECH_ADVANCED_POINT_TRANSFER", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_ADVANCED_POINT_TRANSFER", "info", "2", "name");
                    languageManager.setValue(List.of("{color:normal}Remote Range: {color:number}{1} Blocks"), "items", "FINALTECH_ADVANCED_POINT_TRANSFER", "info", "2", "lore");

                    // FINALTECH_ADVANCED_MESH_TRANSFER

                    languageManager.setValue("{color:prandom}Advanced Mesh Transfer", "items", "FINALTECH_ADVANCED_MESH_TRANSFER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_ADVANCED_MESH_TRANSFER", "info", "1", "name");
                    languageManager.setValue(List.of("{color:normal}Input items to storage and output them"), "items", "FINALTECH_ADVANCED_MESH_TRANSFER", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_ADVANCED_MESH_TRANSFER", "info", "2", "name");
                    languageManager.setValue(List.of("{color:normal}Can use chain to extend range in remote search mode"), "items", "FINALTECH_ADVANCED_MESH_TRANSFER", "info", "2", "lore");

                    // FINALTECH_ADVANCED_LINE_TRANSFER

                    languageManager.setValue("{color:prandom}Advanced Line Transfer", "items", "FINALTECH_ADVANCED_LINE_TRANSFER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_ADVANCED_LINE_TRANSFER", "info", "1", "name");
                    languageManager.setValue(List.of("{color:normal}Transfer items along the place it look at"), "items", "FINALTECH_ADVANCED_LINE_TRANSFER", "info", "1", "lore");

                    // FINALTECH_ADVANCED_LOCATION_TRANSFER

                    languageManager.setValue("{color:prandom}Advanced Location Transfer", "items", "FINALTECH_ADVANCED_LOCATION_TRANSFER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "FINALTECH_ADVANCED_LOCATION_TRANSFER", "info", "1", "name");
                    languageManager.setValue(List.of("{color:normal}Transfer items to the specified location", "{color:normal}Need {id:FINALTECH_LOCATION_RECORDER} {color:normal}to work"), "items", "FINALTECH_ADVANCED_LOCATION_TRANSFER", "info", "1", "lore");

                    // FINALTECH_ADVANCED_AUTO_CRAFT_FRAME

                    languageManager.setValue("{color:prandom}Advanced Auto Craft Frame", "items", "FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "name");

                    languageManager.setValue("{color:stress}Parse", "items", "FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-icon", "name");
                    languageManager.setValue(List.of("{color:action}[click] {color:normal}to parse item"), "items", "FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-icon", "lore");

                    languageManager.setValue("{color:stress}Wiki", "items", "FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "wiki-icon", "name");
                    List<String> lore9 = new ArrayList<>();
                    lore9.add("{color:normal}↓ Put item here");
                    lore9.add("{color:normal}↙ Click and parse it");
                    lore9.add("{color:normal}↑↑ Put machine item in top two lines to parse item more steps");
                    lore9.add("    {color:normal}Available machine item can be found in slimefun guide.");
                    lore9.add("{color:normal}↑ Put quantity module to speed up");
                    lore9.add("{color:negative}The machine can not work at all");
                    languageManager.setValue(lore9, "items", "FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "wiki-icon", "lore");

                    languageManager.setValue("{color:stress}Parse Failed", "items", "FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-failed-icon", "name");
                    languageManager.setValue(List.of("{color:normal}Please try to parse different item"), "items", "FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-failed-icon", "lore");

                    languageManager.setValue("{color:positive}Parse Success", "items", "FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-extend-icon", "name");
                    languageManager.setValue(List.of("{color:action}[click] {color:normal}to get more information"), "items", "FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-extend-icon", "lore");

                    languageManager.setValue("{color:positive}Parse Success", "items", "FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-success-icon", "name");
                    languageManager.setValue("{color:conceal}Amount : {1}", "items", "FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-amount");

                    // FINALTECH_BOX

                    languageManager.setValue("{color:normal}You forget something :D", "items", "FINALTECH_BOX", "message");

                    // FINALTECH_SHINE

                    languageManager.setValue("{color:normal}You forget something :D", "items", "FINALTECH_SHINE", "message");
                    languageManager.setValue("{color:negative}Void Curse", "items", "FINALTECH_SHINE", "info", "2", "name");
                    languageManager.setValue("{color:negative}Notice", "items", "FINALTECH_SHINE", "info", "3", "name");
                    languageManager.setValue("{color:negative}Notice", "items", "FINALTECH_SHINE", "info", "4", "name");
                    languageManager.setValue("{color:negative}Notice", "items", "FINALTECH_SHINE", "info", "5", "name");

                    languageManager.setValue(
                            List.of("{color:normal}While obtaining {id:FINALTECH_SHINE}",
                                    "{color:normal}Player will be cursed by void for a short time"),
                            "items", "FINALTECH_SHINE", "info", "2", "lore");
                    languageManager.setValue(
                            List.of("{color:normal}While carrying {id:FINALTECH_SHINE} {color:normal}or {id:FINALTECH_BOX}",
                                    "{color:normal}The void damage player get will be enlarged greatly"),
                            "items", "FINALTECH_SHINE", "info", "3", "lore");
                    languageManager.setValue(
                            List.of("{color:normal}While carrying {id:FINALTECH_BOX}",
                                    "{color:normal}if player is in low place or cursed by void",
                                    "{color:negative}Player will lose all his items after dead!!!"),
                            "items", "FINALTECH_SHINE", "info", "4", "lore");
                    languageManager.setValue(
                            List.of("{color:normal}While carrying {id:FINALTECH_BOX}",
                                    "{color:normal}if player is in low place or cursed by void",
                                    "{color:negative}Player will lose all his items after dead!!!",
                                    "",
                                    "{color:negative}Soul Bound Rune may be useful"),
                            "items", "FINALTECH_SHINE", "info", "5", "lore");

                    // VOID_CURSE

                    languageManager.setValue("{1} {color:normal}is swallowed by the void.", "effect", "VOID_CURSE", "message", "death");

                    // ManualCraftMachine

                    languageManager.setValue(List.of("{color:normal}Energy: {color:number}{1}J"), "items", "ManualCraftMachine", "craft-icon", "lore");

                    // FINALTECH_ITEM_SERIALIZATION_CONSTRUCTOR

                    List<String> list10 = languageManager.getStringList("items", "FINALTECH_ITEM_SERIALIZATION_CONSTRUCTOR", "copy-card", "lore");
                    list10.add("{color:normal}Rate: {color:number}{4}");
                    languageManager.setValue(list10, "items", "FINALTECH_ITEM_SERIALIZATION_CONSTRUCTOR", "copy-card", "lore");

                    // FINALTECH_MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR

                    List<String> list11 = languageManager.getStringList("items", "FINALTECH_MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR", "copy-card", "lore");
                    list11.add("{color:normal}Rate: {color:number}{4}");
                    languageManager.setValue(list11, "items", "FINALTECH_MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR", "copy-card", "lore");

                    // HELPER-RECIPE_ITEM

                    languageManager.setValue("{color:stress}Display Recipe", "helper", "ICON", "recipe-icon", "name");

                    // MESSAGE-INVALID_ITEM

                    languageManager.setValue("{color:negative}Not valid item!", "message", "invalid-item");

                }
            }
        });

        versionMap.put("20230109", new UpdateFunction() {
            @Override
            protected String targetVersion() {
                return "20230211";
            }

            @Override
            protected void update(FinalTech finalTech) {
                if(configManager.containPath("tweak", "range-limit", "FINALTECH_ITEM_DISMANTLE_TABLE", "drop-self")) {
                    Boolean dropSelf = configManager.getOrDefault(false, "tweak", "range-limit", "FINALTECH_ITEM_DISMANTLE_TABLE", "drop-self");
                    if(dropSelf) {
                        configManager.setValue(false, "tweak", "range-limit", "FINALTECH_ITEM_DISMANTLE_TABLE", "drop-self");
                    }
                }
            }
        });
    }

    public void update(@Nonnull FinalTech finalTech) {
        if(!this.init) {
            this.init();
        }

        String currentVersion = "";

        try {
            currentVersion = FinalTech.getConfigManager().getOrDefault(LATEST_VERSION, "version");
        } catch (Exception e) {
            e.printStackTrace();
            finalTech.getLogger().warning("It seems you have set the wrong value type of version");
            Integer version = FinalTech.getConfigManager().getOrDefault(Integer.parseInt(LATEST_VERSION), "version");
            currentVersion = String.valueOf(version);
        }

        if(LATEST_VERSION.equals(currentVersion)) {
            FinalTech.logger().info("You are using the latest version. Good luck!");
            return;
        }

        UpdateFunction updateFunction = versionMap.get(currentVersion);
        if(updateFunction == null) {
            FinalTech.logger().info("You are using the unknown version. Version updater is disabled.");
        } else {
            String targetVersion = LATEST_VERSION;
            FinalTech.logger().info("Version " + currentVersion + " is detected! Version updater start to work...");
            while (updateFunction != null && !LATEST_VERSION.equals(currentVersion)) {
                targetVersion = updateFunction.apply(finalTech);
                FinalTech.logger().info("FinalTECH Updated: " + currentVersion + " -> " + targetVersion);
                currentVersion = targetVersion;
                updateFunction = versionMap.get(currentVersion);
            }
            FinalTech.getConfigManager().setValue(targetVersion, "version");
            FinalTech.logger().info("You are using the latest version now. Good luck!");
        }
    }

    public void destroy() {
        this.versionMap.clear();
    }

    @Override
    public void accept(@Nonnull FinalTech finalTech) {
        this.update(finalTech);
    }

    @Nonnull
    public static Updater getInstance() {
        if (instance == null) {
            synchronized (Updater.class) {
                if (instance == null) {
                    instance = new Updater();
                }
            }
        }
        return instance;
    }

    protected static abstract class UpdateFunction implements Function<FinalTech, String> {
        protected UpdateFunction() {

        }

        @Override
        public String apply(FinalTech finalTech) {
            this.update(finalTech);
            return this.targetVersion();
        }

        protected abstract void update(FinalTech finalTech);

        protected abstract String targetVersion();
    }
}
