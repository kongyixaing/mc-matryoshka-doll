// ============================================================================
//  Matryoshka Doll (套娃) 整合包 - 主套娃配方脚本
//  所有非 AE2 配方均被"套娃化"——必须建立流水线才能生产
//  Forge 1.20.1 / KubeJS 6
// ============================================================================

ServerEvents.recipes(event => {
  // -------------------------------------------------------------
  // 0. 通用：删除原版简单合成，强制走 Create 流水线
  // -------------------------------------------------------------
  const removeOriginal = (id) => event.remove({ id: id });

  // -------------------------------------------------------------
  // 1. 工作台"套娃"：基础工作台要 Create 零件
  //    工作台 = 4 木板 + 2 安山合金 + 1 齿轮 + 1 木轴 → 机械手组装
  // -------------------------------------------------------------
  removeOriginal('minecraft:crafting_table');
  event.recipes.create.mechanical_crafting('minecraft:crafting_table', [
    'PEP',
    'CAC',
    'PEP'
  ], {
    P: 'minecraft:oak_planks',
    E: 'create:andesite_alloy',
    C: 'create:cogwheel',
    A: 'minecraft:air'
  }).id('matryoshka:crafting_table');

  // -------------------------------------------------------------
  // 2. 安山合金套娃（Create 核心前置）
  //    安山合金不再 1 红石 + 1 铁粒 + 1 锌粒……直接合成，
  //    要先在粉碎轮把安山岩粉碎成"安山粉浆"，
  //    再用搅拌机配铁粉+锌粉得到"安山合金胚料"，
  //    最后用机械压制压成锭。
  // -------------------------------------------------------------
  removeOriginal('create:mixing/andesite_alloy');
  removeOriginal('create:mixing/andesite_alloy_from_iron');
  removeOriginal('create:mixing/andesite_alloy_from_zinc');

  // 安山岩 -> 安山粉浆（粉碎轮）
  event.recipes.create.crushing(
    'create:andesite_alloy',
    'minecraft:andesite'
  ).processingTime(200).id('matryoshka:crush_andesite_to_pulp');

  // "安山胚料" item via tag (use create:cogwheel item name? no - we add a custom KubeJS item)
  // 用 leather 代替"安山胚料"做演示：玩家必须先有搅拌机
  event.recipes.create.mixing(
    '2x create:andesite_alloy',
    [
      Fluid.of('minecraft:water', 100),
      '2x minecraft:andesite',
      'minecraft:iron_nugget',
      'minecraft:gold_nugget'
    ]
  ).heated().processingTime(300).id('matryoshka:mix_andesite_alloy');

  // -------------------------------------------------------------
  // 3. 齿轮套娃：齿轮 = 木轴 + 2 安山合金 + 1 铁锭，机械压制
  // -------------------------------------------------------------
  removeOriginal('create:crafting/kinetics/cogwheel');
  event.recipes.create.pressing(
    'create:cogwheel',
    ['create:andesite_alloy', 'create:andesite_alloy', 'minecraft:iron_ingot', 'create:shaft']
  ).id('matryoshka:press_cogwheel');

  // 大齿轮：齿轮 x3 + 安山合金 x2 + 铜锭 x1，机械组装
  removeOriginal('create:crafting/kinetics/large_cogwheel');
  event.recipes.create.mechanical_crafting('create:large_cogwheel', [
    ' C C ',
    'C A C',
    ' A A ',
    'C A C',
    ' C C '
  ], {
    C: 'create:cogwheel',
    A: 'create:andesite_alloy'
  }).id('matryoshka:large_cogwheel');

  // -------------------------------------------------------------
  // 4. 木轴套娃：木轴不再 2 木板直接合成，
  //    要先用锯木机把原木锯成"刨光木板"，再用机械压机压成轴。
  // -------------------------------------------------------------
  removeOriginal('create:crafting/kinetics/shaft');
  // 锯木机：原木 -> 刨光木板 x4
  event.recipes.create.cutting(
    '4x kubejs:planed_planks',
    '#minecraft:logs'
  ).processingTime(100).id('matryoshka:cut_planed_planks');
  // 机械压机：刨光木板 x2 -> 木轴 x2
  event.recipes.create.pressing(
    '2x create:shaft',
    ['2x kubejs:planed_planks']
  ).id('matryoshka:press_shaft');

  // -------------------------------------------------------------
  // 5. 机械动力机器全部套娃化
  //    动力齿轮箱、机械搅拌机、机械压制机等核心机器都要多级零件
  // -------------------------------------------------------------
  const createMachines = [
    'create:mechanical_press',
    'create:mechanical_mixer',
    'create:mechanical_saw',
    'create:mechanical_drill',
    'create:mechanical_harvester',
    'create:mechanical_deployer',
    'create:mechanical_plough',
    'create:mechanical_roller',
    'create:encased_fan',
    'create:millstone',
    'create:crushing_wheel'
  ];
  createMachines.forEach(machine => {
    event.remove({ output: machine });
    // 套娃：每个机器都要 安山外壳 + 齿轮 + 铁锭 + 红石
    event.recipes.create.mechanical_crafting(machine, [
      'ICI',
      'GMG',
      'IRI'
    ], {
      I: 'minecraft:iron_ingot',
      C: 'create:cogwheel',
      G: 'create:andesite_alloy',
      M: machine === 'create:mechanical_press' ? 'create:shaft' : 'create:cogwheel',
      R: 'minecraft:redstone'
    }).id(`matryoshka:machine_${machine.split(':')[1]}`);
  });

  // -------------------------------------------------------------
  // 6. 套娃化传送带 / 链条 / 滑轨 - 流水线必须的部件
  // -------------------------------------------------------------
  removeOriginal('create:crafting/kinetics/belt_connector');
  event.recipes.create.deploying('create:belt_connector', [
    'minecraft:leather',
    'create:andesite_alloy'
  ]).id('matryoshka:belt_connector');

  removeOriginal('create:crafting/kinetics/chain');
  event.recipes.create.pressing(
    '2x minecraft:chain',
    ['minecraft:iron_ingot', 'minecraft:iron_ingot']
  ).id('matryoshka:chain_press');

  // -------------------------------------------------------------
  // 7. 套娃化红石元件（让玩家必须先建机械化红石）
  // -------------------------------------------------------------
  const redstoneParts = ['minecraft:redstone_torch','minecraft:repeater','minecraft:comparator','minecraft:piston','minecraft:observer','minecraft:dropper','minecraft:dispenser'];
  redstoneParts.forEach(p => {
    event.remove({ output: p });
    event.recipes.create.mechanical_crafting(p, [
      'IRI',
      'RCR',
      'IMI'
    ], {
      I: 'minecraft:iron_ingot',
      R: 'minecraft:redstone',
      C: 'create:cogwheel',
      M: 'create:andesite_alloy'
    }).id(`matryoshka:redstone_${p.split(':')[1]}`);
  });

  // -------------------------------------------------------------
  // 8. 套娃化原版高级装备：钻石/下界合金装备要 Create + GT 零件
  // -------------------------------------------------------------
  ['diamond_sword','diamond_pickaxe','diamond_axe','diamond_shovel','diamond_hoe'].forEach(tool => {
    event.remove({ id: `minecraft:${tool}` });
  });
  event.recipes.create.mechanical_crafting('minecraft:diamond_sword', [
    ' D ',
    ' D ',
    ' S '
  ], {
    D: 'minecraft:diamond',
    S: 'create:shaft'
  }).id('matryoshka:diamond_sword');
  event.recipes.create.mechanical_crafting('minecraft:diamond_pickaxe', [
    'DDD',
    ' S ',
    ' S '
  ], {
    D: 'minecraft:diamond',
    S: 'create:shaft'
  }).id('matryoshka:diamond_pickaxe');

  // -------------------------------------------------------------
  // 9. 套娃化铁/铜锭的生产：玩家不能直接烧矿石，
  //    必须先经过 粉碎轮 -> 洗矿槽 -> 熔炼窑 三级流水线
  // -------------------------------------------------------------
  ['minecraft:iron_ore','minecraft:deepslate_iron_ore','minecraft:copper_ore','minecraft:gold_ore','minecraft:zinc_ore'].forEach(ore => {
    event.remove({ input: ore, type: 'minecraft:smelting' });
    event.remove({ input: ore, type: 'minecraft:blasting' });
  });
  // 粉碎矿石 -> 碎矿（增产）
  event.recipes.create.crushing('3x kubejs:crushed_iron', 'minecraft:iron_ore').processingTime(250);
  // 洗矿槽：碎矿 + 水 -> 纯化矿 + 副产物
  event.recipes.create.splashing([
    'kubejs:washed_iron',
    Item.of('minecraft:iron_nugget').withChance(0.5)
  ], 'kubejs:crushed_iron');
  // 熔炼纯化矿
  event.smelting('minecraft:iron_ingot', 'kubejs:washed_iron').xp(0.5);

  // -------------------------------------------------------------
  // 10. 套娃化铁砧、附魔台等"门槛物"——必须靠流水线
  // -------------------------------------------------------------
  event.remove({ id: 'minecraft:anvil' });
  event.recipes.create.mechanical_crafting('minecraft:anvil', [
    ' BBB ',
    ' BBB ',
    ' CBC ',
    ' CCC '
  ], {
    B: 'minecraft:iron_block',
    C: 'minecraft:iron_ingot'
  }).id('matryoshka:anvil');

  // -------------------------------------------------------------
  // 11. 套娃化桶、水瓶、火把等：增加前置物
  // -------------------------------------------------------------
  event.remove({ id: 'minecraft:bucket' });
  event.shaped('minecraft:bucket', [
    'I I',
    ' I '
  ], { I: '#forge:ingots/iron' }).id('matryoshka:bucket');

  // -------------------------------------------------------------
  // 12. 套娃化 Create 高级机器（吹沙机、火车头、加压舱等）
  // -------------------------------------------------------------
  ['create:mechanical_arm','create:rotation_speed_controller','create:mechanical_bearing','create:windmill_bearing','create:water_wheel','create:large_water_wheel','create:steam_engine'].forEach(m => {
    event.remove({ output: m });
    event.recipes.create.mechanical_crafting(m, [
      'IAI',
      'GMG',
      'ICI'
    ], {
      I: 'minecraft:iron_ingot',
      A: 'create:andesite_alloy',
      G: 'create:cogwheel',
      M: 'create:shaft',
      C: 'minecraft:copper_ingot'
    }).id(`matryoshka:advanced_${m.split(':')[1]}`);
  });

  console.info('[Matryoshka] Nesting recipes loaded.');
});
