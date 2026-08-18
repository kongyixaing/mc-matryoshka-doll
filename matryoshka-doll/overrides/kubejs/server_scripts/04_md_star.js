// ============================================================================
//  Matryoshka Doll - MD之星（MD Star）毕业配方脚本
//  目标：让玩家"必须把所有 mod 都玩一遍"才能拿到 MD之星
//
//  设计：
//    MD之星 的合成被拆成 9 个"mod 精华"组件 + 1 个 MD 框架，
//    每个组件都来自不同 mod 的终末产物。
//    最终的 MD之星 在 md-star-mod 自定义多方块结构 "MD 组装塔" 中合成。
//    （最终合成本身由 mod 程序化处理，这里只定义各组件的获取路径）
// ============================================================================

ServerEvents.recipes(event => {
  // -------------------------------------------------------------
  // 1. "MD 框架"（MD Frame）——MD之星 的核心框架
  //    要 GT 最顶级机壳 + AE2 量子环 + Create 终极齿轮
  // -------------------------------------------------------------
  event.remove({ output: 'mdstar:md_frame' });
  event.recipes.create.mechanical_crafting('mdstar:md_frame', [
    'G C G',
    'C Q C',
    'G C G'
  ], {
    G: 'create:large_cogwheel',
    C: 'gtceu:uv_machine_hull',      // GT 最顶级机壳
    Q: 'ae2:quantum_ring'             // AE2 量子环
  }).id('matryoshka:md_frame');

  // -------------------------------------------------------------
  // 2. 各 mod "精华" 组件 —— 9 个组件，对应 9 个 mod 阶段
  //    每个组件的配方都被"套娃化"（需要该 mod 的多步产物）
  // -------------------------------------------------------------

  // (1) Create 精华 — 机械动力终极齿轮
  event.recipes.create.mechanical_crafting('mdstar:create_essence', [
    ' G G ',
    'G A G',
    ' A A ',
    'G A G',
    ' G G '
  ], {
    G: 'create:large_cogwheel',
    A: 'create:precision_mechanism'
  }).id('matryoshka:create_essence');

  // (2) Create 附属：Steam 'n' Rails — 火车头（轨道车）核心
  event.recipes.create.mechanical_crafting('mdstar:rails_essence', [
    ' T T ',
    'TCTCT',
    ' T T '
  ], {
    T: 'create:track',
    C: 'create:track_station'
  }).id('matryoshka:rails_essence');

  // (3) Create: Slice & Dice — 食物处理机核心
  event.recipes.create.deploying('mdstar:slice_essence', [
    'create:precision_mechanism',
    'createaddition:rolling_mill'
  ]).id('matryoshka:slice_essence');

  // (4) Create: Crafts & Additions — 电力 + 红石融合器
  event.recipes.create.mechanical_crafting('mdstar:cna_essence', [
    'CAB',
    'RMR',
    'CAB'
  ], {
    C: 'createaddition:alternator',
    A: 'createaddition:accumulator',
    B: 'createaddition:spool',
    R: 'minecraft:redstone_block',
    M: 'create:precision_mechanism'
  }).id('matryoshka:cna_essence');

  // (5) GregTech CEu — 最顶级电路（终极电路）
  event.recipes.create.mechanical_crafting('mdstar:gregtech_essence', [
    'PCP',
    'CFC',
    'PCP'
  ], {
    P: 'gtceu:ultimate_circuit',
    C: 'gtceu:overpowered_circuit',
    F: 'gtceu:uv_machine_hull'
  }).id('matryoshka:gregtech_essence');

  // (6) Applied Energistics 2 — 量子链接室
  event.recipes.create.mechanical_crafting('mdstar:ae2_essence', [
    'Q Q',
    ' K ',
    'Q Q'
  ], {
    Q: 'ae2:quantum_link_chamber',
    K: 'ae2:quantum_ring'
  }).id('matryoshka:ae2_essence');

  // (7) AE2 Things / MEGACells — 终极存储元件
  event.recipes.create.mechanical_crafting('mdstar:ae2_things_essence', [
    'M M',
    ' C ',
    'M M'
  ], {
    M: 'ae2things:quantum_storage_block',
    C: 'ae2:item_storage_cell_256k'
  }).id('matryoshka:ae2_things_essence');

  // (8) 暮色森林 — 最终 BOSS 掉落物（娜迦 / 巫妖 / 雪怪 / 九头蛇 / 幻影骑士 / 幽灵骑士 / 龙）
  event.recipes.create.mechanical_crafting('mdstar:twilight_essence', [
    ' N L ',
    'H I K',
    ' D G '
  ], {
    N: 'twilightforest:naga_trophy',
    L: 'twilightforest:lich_trophy',
    H: 'twilightforest:hydra_trophy',
    I: 'twilightforest:knight_phantom_trophy',
    K: 'twilightforest:ur_ghast_trophy',
    D: 'twilightforest:snow_queen_trophy',
    G: 'twilightforest:dragon_trophy'
  }).id('matryoshka:twilight_essence');

  // (9) 探索 mod 精华 — 来自 YUNG's & Dungeons Arise 的稀有战利品
  //    代表"探索完成"——给探索类 mod 的最终 boss 掉落
  event.recipes.create.mechanical_crafting('mdstar:explore_essence', [
    'A B Y',
    'R M E',
    'D C T'
  ], {
    A: 'dungeons_arise:gold_diamond_block',
    B: 'dungeons_arise:warden_key',
    Y: 'repurposed_structures:bastion_treasure',
    R: 'dungeons_arise:royal_key',
    M: 'minecraft:enchanted_golden_apple',
    E: 'minecraft:dragon_egg',
    D: 'minecraft:nether_star',
    C: 'minecraft:elytra',
    T: 'minecraft:totem_of_undying'
  }).id('matryoshka:explore_essence');

  // -------------------------------------------------------------
  // 3. "MD 之心"（MD Heart）—— 9 个精华 + MD 框架
  //    通过机械组装合成（仍要机械化）
  // -------------------------------------------------------------
  event.remove({ output: 'mdstar:md_heart' });
  event.recipes.create.mechanical_crafting('mdstar:md_heart', [
    '123',
    '4F5',
    '678'
  ], {
    '1': 'mdstar:create_essence',
    '2': 'mdstar:rails_essence',
    '3': 'mdstar:slice_essence',
    '4': 'mdstar:cna_essence',
    'F': 'mdstar:md_frame',
    '5': 'mdstar:gregtech_essence',
    '6': 'mdstar:ae2_essence',
    '7': 'mdstar:ae2_things_essence',
    '8': 'mdstar:twilight_essence'
  }).id('matryoshka:md_heart');

  // -------------------------------------------------------------
  // 4. "MD 之星壳"（MD Star Shell）—— MD 之心 + 探索精华
  // -------------------------------------------------------------
  event.remove({ output: 'mdstar:md_star_shell' });
  event.recipes.create.mechanical_crafting('mdstar:md_star_shell', [
    ' X ',
    'XHX',
    ' X '
  ], {
    H: 'mdstar:md_heart',
    X: 'mdstar:explore_essence'
  }).id('matryoshka:md_star_shell');

  // -------------------------------------------------------------
  // 5. 最终的"MD 之星"（MD Star）—— 只能在 md-star-mod 的
  //    "MD 组装塔"多方块结构中合成（自定义配方由 mod 提供）
  //    这里仅作为占位"备用合成"以防玩家卡死。
  //    推荐：mod 在 mdstar:recipes/md_star_assembly.json 注册专用配方。
  // -------------------------------------------------------------
  // (备用)机械合成
  event.remove({ output: 'mdstar:md_star' });
  event.recipes.create.mechanical_crafting('mdstar:md_star', [
    '       S       ',
    '      SSS      ',
    '     SSHSS     ',
    '    SSHSHSS    ',
    '   SSHSSSHSS   ',
    '  SSHSHSHSHSS  ',
    ' SSHSSHSHSSHSS ',
    'SSSHSHSHSHSHSSS',
    ' SSHSSHSHSSHSS ',
    '  SSHSHSHSHSS  ',
    '   SSHSSSHSS   ',
    '    SSHSHSS    ',
    '     SSHSS     ',
    '      SSS      ',
    '       S       '
  ], {
    H: 'mdstar:md_heart',
    S: 'mdstar:md_star_shell'
  }).id('matryoshka:md_star_emergency');

  console.info('[Matryoshka] MD Star graduation recipes loaded.');
});
