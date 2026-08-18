// ============================================================================
//  Matryoshka Doll - KubeJS 启动脚本
//  注册整合包使用的自定义中间物（不与 mdstar mod 重复）。
//  md_frame / md_heart / *_essence 等毕业物由 mdstar mod 自己注册，
//  这里只注册 KubeJS 自己用的中间材料（在 kubejs: 命名空间下）。
//  Forge 1.20.1 / KubeJS 6
// ============================================================================

StartupEvents.registry('item', event => {
  // -------------------------------------------------------------
  // 矿石处理中间物（套娃矿石链）
  // -------------------------------------------------------------
  event.create('planed_planks').displayName('刨光木板').texture('minecraft:oak_planks');
  event.create('crushed_iron').displayName('碎铁矿石').texture('minecraft:iron_ore');
  event.create('washed_iron').displayName('洗净铁矿石').texture('minecraft:iron_ore');
  event.create('crushed_copper').displayName('碎铜矿石').texture('minecraft:copper_ore');
  event.create('washed_copper').displayName('洗净铜矿石').texture('minecraft:copper_ore');
  event.create('crushed_gold').displayName('碎金矿石').texture('minecraft:gold_ore');
  event.create('washed_gold').displayName('洗净金矿石').texture('minecraft:gold_ore');
  event.create('crushed_zinc').displayName('碎锌矿石').texture('create:zinc_ore');
  event.create('washed_zinc').displayName('洗净锌矿石').texture('create:zinc_ore');
});

StartupEvents.registry('block', event => {
  // 暂不创建额外方块——mdstar mod 已提供毕业相关方块。
});
