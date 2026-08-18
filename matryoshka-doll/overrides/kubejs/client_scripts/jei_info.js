// ============================================================================
//  Matryoshka Doll - JEI 信息脚本
//  在 JEI 物品描述里给出"该玩哪个 mod"的提示
// ============================================================================

JEIEvents.addItems(event => {
  // 隐藏 md_star_emergency 备用配方展示——避免玩家直接走捷径
  event.hide('matryoshka:md_star_emergency');
});

ItemEvents.tooltip(event => {
  // 给 MD 之星一个特殊提示
  event.add('mdstar:md_star', '§6★ 整合包毕业物 ★ §7只在 MD 之星合成阵列中合成');
  event.add('mdstar:md_heart', '§b8 个 mod 精华的融合体');
  event.add('mdstar:md_frame', '§7框架：GT UV 机壳 + AE2 量子环 + Create 大齿轮');
});
