// ============================================================================
//  Matryoshka Doll - Applied Energistics 2 简化配方
//  目标：让 AE2 成为整合包中相对"友好"的存储/自动化系统，
//  解除原版 AE2 的繁琐前置（充能器、石英、2 种处理器等）。
//  其余 mod 配方仍套娃化，玩家必须玩 AE2 才能省事。
// ============================================================================

ServerEvents.recipes(event => {
  // -------------------------------------------------------------
  // 1. 简化"梅克伊物质"——直接由石英粉烧成（不必两步）
  // -------------------------------------------------------------
  event.remove({ id: 'ae2:inscriber/silicon_print' });
  event.smelting('ae2:silicon', 'minecraft:quartz').xp(0).id('matryoshka:ae2_silicon_simple');

  // -------------------------------------------------------------
  // 2. 简化处理器：处理器 = 硅 + 红石 + 对应染料
  //    （取消原版的两步"印记+组装"）
  // -------------------------------------------------------------
  event.remove({ id: /^ae2:inscriber\/calculation_processor.*/ });
  event.remove({ id: /^ae2:inscriber\/logic_processor.*/ });
  event.remove({ id: /^ae2:inscriber\/engineering_processor.*/ });
  event.remove({ id: /^ae2:inscriber\/.*processor.*/ });

  // 用压板器一步合成处理器
  event.recipes.create.pressing(
    'ae2:calculation_processor',
    ['ae2:silicon', 'minecraft:redstone', 'minecraft:blue_dye']
  ).id('matryoshka:ae2_calc_processor_simple');
  event.recipes.create.pressing(
    'ae2:logic_processor',
    ['ae2:silicon', 'minecraft:redstone', 'minecraft:cyan_dye']
  ).id('matryoshka:ae2_logic_processor_simple');
  event.recipes.create.pressing(
    'ae2:engineering_processor',
    ['ae2:silicon', 'minecraft:redstone', 'minecraft:yellow_dye']
  ).id('matryoshka:ae2_eng_processor_simple');

  // -------------------------------------------------------------
  // 3. 简化"控制器"——不必 Nether Quartz、不必 Glowstone
  //    控制器 = 玻璃 + 红石 + 1 个处理器
  // -------------------------------------------------------------
  event.remove({ id: 'ae2:network/controller' });
  event.shaped('ae2:controller', [
    'GPG',
    'RCR',
    'GPG'
  ], {
    G: 'minecraft:glass',
    P: 'ae2:calculation_processor',
    C: 'minecraft:copper_block',
    R: 'minecraft:redstone'
  }).id('matryoshka:ae2_controller_simple');

  // -------------------------------------------------------------
  // 4. 简化"驱动器"——只用 1 处理器
  // -------------------------------------------------------------
  event.remove({ id: 'ae2:network/drive' });
  event.shaped('ae2:drive', [
    'GIG',
    'IRI',
    'GPG'
  ], {
    G: 'minecraft:glass',
    I: 'minecraft:iron_ingot',
    R: 'minecraft:redstone',
    P: 'ae2:logic_processor'
  }).id('matryoshka:ae2_drive_simple');

  // -------------------------------------------------------------
  // 5. 简化"存储元件"——直接 = 玻璃 + 红石
  // -------------------------------------------------------------
  ['1k','4k','16k','64k','256k'].forEach(tier => {
    event.remove({ id: `ae2:network/cells/item_storage_components_${tier}` });
  });
  event.shaped('ae2:item_storage_cell_1k', [
    'GRG',
    'R R',
    'GGG'
  ], { G: 'minecraft:glass', R: 'minecraft:redstone' }).id('matryoshka:ae2_cell_1k_simple');
  event.shaped('ae2:item_storage_cell_4k', [
    'GRG',
    'RCR',
    'GRG'
  ], { G: 'minecraft:glass', R: 'minecraft:redstone', C: 'ae2:calculation_processor' }).id('matryoshka:ae2_cell_4k_simple');
  event.shaped('ae2:item_storage_cell_16k', [
    'GRG',
    'PCP',
    'GRG'
  ], { G: 'minecraft:glass', R: 'minecraft:redstone', C: 'ae2:calculation_processor', P: 'ae2:logic_processor' }).id('matryoshka:ae2_cell_16k_simple');
  event.shaped('ae2:item_storage_cell_64k', [
    'PRP',
    'RCR',
    'PRP'
  ], { P: 'ae2:logic_processor', R: 'minecraft:redstone', C: 'ae2:engineering_processor' }).id('matryoshka:ae2_cell_64k_simple');
  event.shaped('ae2:item_storage_cell_256k', [
    'ERE',
    'RCR',
    'ERE'
  ], { E: 'ae2:engineering_processor', R: 'minecraft:redstone', C: 'ae2:logic_processor' }).id('matryoshka:ae2_cell_256k_simple');

  // -------------------------------------------------------------
  // 6. 简化"梅克伊物质"——直接 = 石英块 + 红石粉
  // -------------------------------------------------------------
  event.remove({ id: 'ae2:transform/matter' });
  event.smelting('ae2:matter_ball', 'minecraft:quartz').xp(0).id('matryoshka:ae2_matter_simple');
  event.recipes.create.pressing('ae2:fluix_crystal', ['minecraft:quartz', 'minecraft:redstone', 'minecraft:glowstone_dust']).id('matryoshka:ae2_fluix_simple');

  // -------------------------------------------------------------
  // 7. 简化"无线终端"——只 1 处理器 + 1 玻璃
  // -------------------------------------------------------------
  event.remove({ id: /^ae2:.*wireless_terminal.*/ });
  event.remove({ id: 'ae2:tools/wireless_terminal' });
  event.shaped('ae2:wireless_crafting_terminal', [
    'EPE',
    'PGP',
    'EPE'
  ], {
    E: 'ae2:engineering_processor',
    P: 'minecraft:ender_pearl',
    G: 'minecraft:glass'
  }).id('matryoshka:ae2_wireless_simple');

  // -------------------------------------------------------------
  // 8. 简化"模式终端 / 物品面板"——直接用铁锭 + 处理器
  // -------------------------------------------------------------
  event.remove({ id: 'ae2:network/pattern_provider' });
  event.shaped('ae2:pattern_provider', [
    'IRI',
    'RPR',
    'IRI'
  ], { I: 'minecraft:iron_ingot', R: 'minecraft:redstone', P: 'ae2:logic_processor' }).id('matryoshka:ae2_pattern_provider_simple');

  event.remove({ id: 'ae2:network/interface' });
  event.shaped('ae2:interface', [
    'GRG',
    'RPR',
    'GGG'
  ], { G: 'minecraft:glass', R: 'minecraft:redstone', P: 'ae2:calculation_processor' }).id('matryoshka:ae2_interface_simple');

  // -------------------------------------------------------------
  // 9. 简化"ME 玻璃缆线"——直接 = 玻璃 + 红石
  // -------------------------------------------------------------
  event.remove({ id: /^ae2:network\/cables\/.*/ });
  event.shaped('8x ae2:glass_cable', [
    'GGG',
    'R R',
    'GGG'
  ], { G: 'minecraft:glass', R: 'minecraft:redstone' }).id('matryoshka:ae2_glass_cable_simple');
  event.shaped('8x ae2:smart_cable', [
    'GRG',
    'R R',
    'GRG'
  ], { G: 'ae2:glass_cable', R: 'minecraft:redstone' }).id('matryoshka:ae2_smart_cable_simple');

  // -------------------------------------------------------------
  // 10. 简化量子环 / 量子链接室（最终空间压缩用，但仍是简化的）
  // -------------------------------------------------------------
  event.remove({ id: 'ae2:network/quantum_ring' });
  event.shaped('ae2:quantum_ring', [
    'EPE',
    'PCP',
    'EPE'
  ], {
    E: 'ae2:engineering_processor',
    P: 'ae2:logic_processor',
    C: 'minecraft:diamond_block'
  }).id('matryoshka:ae2_quantum_ring_simple');

  event.remove({ id: 'ae2:network/quantum_link_chamber' });
  event.shaped('ae2:quantum_link_chamber', [
    'EPE',
    'PGP',
    'EPE'
  ], {
    E: 'ae2:engineering_processor',
    P: 'minecraft:ender_pearl',
    G: 'minecraft:glass'
  }).id('matryoshka:ae2_quantum_chamber_simple');

  console.info('[Matryoshka] AE2 simplified recipes loaded.');
});
