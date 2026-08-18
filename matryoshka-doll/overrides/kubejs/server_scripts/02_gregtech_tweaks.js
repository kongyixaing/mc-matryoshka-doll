// ============================================================================
//  Matryoshka Doll - GregTech CEu Modern 魔改脚本
//  目标：
//    1) 移除所有 GT 基础工具/武器/armor（玩家用原版或 Create 工具）
//    2) 只保留蒸汽时代及之后的内容（蒸汽锅炉、蒸汽机、青铜机器、LV+）
//    3) 把 GT 后期机器的配方材料全部套娃化（需要 Create + 别的 mod 零件）
//  Forge 1.20.1 / KubeJS 6 / GTCEu Modern
// ============================================================================

ServerEvents.recipes(event => {
  // -------------------------------------------------------------
  // 1. 移除所有 GT 基础工具/武器/装备
  //    （GT 自带的"廉价工具"系统——木/石/青铜/铁/钢/钻石/下界合金 套装）
  // -------------------------------------------------------------
  // 工具
  ['sword','pickaxe','shovel','axe','hoe','hammer','file','saw','wrench','screwdriver','mallet','knife','crowbar','plunger','scoop','branch_hatchet','spade','rolling_pin','pickaxe_head','shovel_head','axe_head','sword_head','hoe_head','file_head','hammer_head','saw_head','wrench_head','screwdriver_head'].forEach(part => {
    event.remove({ output: new RegExp(`gtceu:.*${part}$`) });
    event.remove({ id: new RegExp(`gtceu:.*${part}$`) });
  });
  // 装备
  ['helmet','chestplate','leggings','boots'].forEach(part => {
    event.remove({ output: new RegExp(`gtceu:.*${part}$`) });
    event.remove({ id: new RegExp(`gtceu:.*${part}$`) });
  });
  // GT 工具组件（如各种 head/plate/rod）
  event.remove({ output: /^gtceu:.*_head$/ });
  event.remove({ output: /^gtceu:.*_plate$/ });
  event.remove({ output: /^gtceu:.*_rod$/ });
  event.remove({ output: /^gtceu:.*_ring$/ });
  event.remove({ output: /^gtceu:.*_screw$/ });

  // -------------------------------------------------------------
  // 2. 移除 GT 原始（无机械）冶炼工具：
  //    如 gtceu:crucible, gtceu:primitive_blast_furnace 等"早期手动"机器
  //    只留蒸汽锅炉 + 蒸汽机 + 青铜机器 + LV 及之后
  // -------------------------------------------------------------
  const primitiveMachines = [
    'gtceu:crucible',                  // 石锅熔炼
    'gtceu:primitive_blast_furnace',   // 原始高炉
    'gtceu:firebrick',                 // 耐火砖
    'gtceu:coke_oven_bricks',          // 焦炉砖
    'gtceu:clay_dust',                 // 黏土粉
    'gtceu:coke_oven',                 // 焦炉
    'gtceu:bronze_plated_bricks',      // 青铜镀砖（保留）
    'gtceu:primitive_pump'             // 原始泵
  ];
  primitiveMachines.forEach(m => event.remove({ output: m }));

  // -------------------------------------------------------------
  // 3. GT 蒸汽时代机器——套娃化（要 Create 零件）
  //    玩家必须先做出机械动力流水线，才能进入 GT 蒸汽时代
  // -------------------------------------------------------------
  const steamAgeMachines = [
    'gtceu:lp_steam_boiler',          // 低压蒸汽锅炉
    'gtceu:hp_steam_boiler',          // 高压蒸汽锅炉
    'gtceu:steam_turbine',             // 蒸汽轮机
    'gtceu:bronze_hull',               // 青铜外壳
    'gtceu:steel_hull',                // 钢制外壳
    'gtceu:bronze_machine_casing',    // 青铜机器外壳
    'gtceu:steel_machine_casing'      // 钢制机器外壳
  ];
  steamAgeMachines.forEach(machine => {
    event.remove({ output: machine });
    // 套娃：每个 GT 机器 = 自己材料 + Create 齿轮 + 安山合金 + 红石 + 铁锭
    event.recipes.create.mechanical_crafting(machine, [
      'I I',
      'GCG',
      'IRI'
    ], {
      I: 'minecraft:iron_ingot',
      G: 'create:cogwheel',
      C: 'create:andesite_alloy',
      R: 'minecraft:redstone'
    }).id(`matryoshka:gt_${machine.split(':')[1]}`);
  });

  // -------------------------------------------------------------
  // 4. 套娃化 LV/MV/HV 时代的核心机器（电机器、电动处理机等）
  //    这些是 GT 的核心：玩家必须同时玩 Create + AE2 + 别的 mod 才能造出来
  //    材料 = 各 mod 的高端材料 + GT 板/线 + Create 齿轮 + AE2 处理器
  // -------------------------------------------------------------
  const gtMachines = [
    'gtceu:lv_machine_hull',           // LV 机壳
    'gtceu:mv_machine_hull',            // MV 机壳
    'gtceu:hv_machine_hull',           // HV 机壳
    'gtceu:ev_machine_hull',            // EV 机壳
    'gtceu:iv_machine_hull',            // IV 机壳
    'gtceu:luv_machine_hull',           // LuV 机壳
    'gtceu:zpm_machine_hull',           // ZPM 机壳
    'gtceu:uv_machine_hull',            // UV 机壳
    'gtceu:lv_energy_input_hatch',      // 输入仓
    'gtceu:lv_energy_output_hatch',
    'gtceu:lv_item_input_bus',
    'gtceu:lv_item_output_bus',
    'gtceu:lv_fluid_input_hatch',
    'gtceu:lv_fluid_output_hatch'
  ];
  gtMachines.forEach(machine => {
    event.remove({ output: machine });
    // 套娃：LV 机壳 = 钢板 + 铜线 + 红石 + Create 齿轮 + GT 板
    event.recipes.create.mechanical_crafting(machine, [
      'SPS',
      'WGW',
      'SRS'
    ], {
      S: 'minecraft:iron_ingot',
      P: '#forge:plates/iron',
      W: '#forge:wires/copper',
      G: 'create:cogwheel',
      R: 'minecraft:redstone'
    }).id(`matryoshka:gt_${machine.split(':')[1]}`);
  });

  // -------------------------------------------------------------
  // 5. 套娃化 GT 化学反应釜 / 蒸馏室 / 电解机 / 装配线
  //    每个核心 GT 机器的配方都要被替换成多步骤流水线产物
  // -------------------------------------------------------------
  const gtProcessors = [
    'gtceu:lv_chemical_bath',
    'gtceu:lv_chemical_reactor',
    'gtceu:lv_distillery',
    'gtceu:lv_electrolyzer',
    'gtceu:lv_centrifuge',
    'gtceu:lv_sifter',
    'gtceu:lv_macerator',
    'gtceu:lv_compressor',
    'gtceu:lv_extractor',
    'gtceu:lv_hammer',
    'gtceu:lv_alloy_smelter',
    'gtceu:lv_assembler',
    'gtceu:lv_bender',
    'gtceu:lv_wiremill',
    'gtceu:lv_cutter',
    'gtceu:lv_lathe',
    'gtceu:lv_forming_press',
    'gtceu:lv_polarizer',
    'gtceu:lv_mixer'
  ];
  gtProcessors.forEach(machine => {
    event.remove({ output: machine });
    // 套娃：每台机器都要 LV机壳 + 齿轮 + 电路板 + 红石 + 各 mod 的零件
    event.recipes.create.mechanical_crafting(machine, [
      'CPC',
      'HRH',
      'CGC'
    ], {
      C: '#forge:plates/iron',
      P: '#forge:plates/copper',
      H: 'gtceu:lv_machine_hull',
      R: 'minecraft:redstone',
      G: 'create:cogwheel'
    }).id(`matryoshka:gt_${machine.split(':')[1]}`);
  });

  // -------------------------------------------------------------
  // 6. 套娃化 GT 的电路板与处理器（LV/MV/HV/...）
  //    要求 AE2 的处理器作为零件，强制玩家玩 AE2
  // -------------------------------------------------------------
  [
    'gtceu:basic_electronic_circuit',
    'gtceu:good_electronic_circuit',
    'gtceu:advanced_circuit',
    'gtceu:extreme_circuit',
    'gtceu:elite_circuit',
    'gtceu:master_circuit',
    'gtceu:ultimate_circuit',
    'gtceu:overpowered_circuit'
  ].forEach(circuit => {
    event.remove({ output: circuit });
    // 套娃：电路板 = GT板 + AE2 处理器 + 红石 + 发光石 + 各 mod 零件
    event.recipes.create.mechanical_crafting(circuit, [
      'PRP',
      'CAL',
      'PGP'
    ], {
      P: '#forge:plates/iron',
      R: 'minecraft:redstone',
      C: 'ae2:calculation_processor',
      A: 'ae2:logic_processor',
      L: 'minecraft:lapis_lazuli',
      G: 'minecraft:glowstone_dust'
    }).id(`matryoshka:gt_${circuit.split(':')[1]}`);
  });

  // -------------------------------------------------------------
  // 7. 套娃化关键材料：钢板、铜板、线材（GT 板/线）
  //    玩家不能"一块锭手敲"——必须经过 GT 弯板机/线材机
  // -------------------------------------------------------------
  ['iron','gold','copper','tin','bronze','steel','aluminium','titanium','tungsten','chrome','stainless_steel','naquadah'].forEach(m => {
    // 删除原版手敲板（如果有）
    event.remove({ id: `gtceu:shaped/plate_${m}_ingot` });
    event.remove({ id: `gtceu:shaped/plate_${m}` });
    event.remove({ id: `gtceu:shaped/foil_${m}` });
    event.remove({ id: `gtceu:shaped/wire_${m}` });
    event.remove({ id: `gtceu:shaped/wire_${m}_single` });
    // 套娃：板 = 锭 x2 + 锤子（机械化弯板机）
    event.recipes.create.pressing(
      `gtceu:${m}_plate`,
      [`#forge:ingots/${m}`, '#forge:tools/files']
    ).id(`matryoshka:gt_plate_${m}`);
    // 套娃：线 = 板 x1 + 剪刀（机械化线材机）
    event.recipes.create.pressing(
      `2x gtceu:${m}_wire`,
      [`#forge:plates/${m}`, '#forge:tools/shears']
    ).id(`matryoshka:gt_wire_${m}`);
  });

  console.info('[Matryoshka] GregTech tweaks loaded.');
});

// ============================================================================
// JEI 隐藏：把 GT 的基础工具从 JEI 里去掉（让玩家完全看不到）
// ============================================================================
JEIEvents.hide(event => {
  // 工具与武器
  ['sword','pickaxe','shovel','axe','hoe','hammer','file','saw','wrench','screwdriver','mallet','knife','crowbar','plunger','scoop','branch_hatchet','spade'].forEach(part => {
    event.hide(new RegExp(`gtceu:.*${part}$`));
  });
  // 装备
  ['helmet','chestplate','leggings','boots'].forEach(part => {
    event.hide(new RegExp(`gtceu:.*${part}$`));
  });
  // 早期手动机器
  ['gtceu:crucible','gtceu:primitive_blast_furnace','gtceu:coke_oven','gtceu:primitive_pump'].forEach(item => {
    event.hide(item);
  });
});
