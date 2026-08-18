# 《Matryoshka Doll（套娃）》整合包 - 试玩部署说明

> 版本：v0.2.0-alpha | Forge 1.20.1 | 包含 FTB Quests 任务系统

---

## 1. 快速开始

### 方法 A：Prism Launcher 导入（推荐）

1. 下载整合包 zip：`matryoshka-doll-v0.2.0-alpha.zip`
2. 打开 Prism Launcher → 添加实例 → 导入 zip → 选择 zip 文件
3. Prism 会自动：
   - 安装 Forge 47.3.12
   - 解压 `overrides/` 到实例目录
   - 所有 28 个 mod jar 已包含在 `overrides/mods/` 中，**无需额外下载**
4. 启动游戏！

### 方法 B：CurseForge App 导入

1. CurseForge App → Minecraft → 创建自定义配置 → 导入
2. 选择 `matryoshka-doll-v0.2.0-alpha.zip`
3. 启动游戏

### 方法 C：手动安装

1. 安装 Forge 1.20.1-47.3.12
2. 将 zip 中的 `overrides/` 目录内容解压到 `.minecraft/` 目录
   - `overrides/mods/*.jar` → `.minecraft/mods/`
   - `overrides/config/` → `.minecraft/config/`
   - `overrides/kubejs/` → `.minecraft/kubejs/`
3. 启动游戏

---

## 2. 包含内容

### 28 个 mod（94MB，全部预装）

| 类别 | mod | 版本 |
| --- | --- | --- |
| 任务系统 | FTB Quests | 2001.4.9 |
| 任务依赖 | FTB Library | 2001.2.9 |
| 任务依赖 | FTB Teams | 2001.3.2 |
| 脚本引擎 | KubeJS | 2001.6.5 |
| 脚本引擎 | Rhino | 2001.2.3 |
| 核心机械 | Create | 1.20.1-6.0.8 |
| 核心科技 | GregTech CEu Modern | 1.20.1-7.5.3 |
| 核心存储 | Applied Energistics 2 | 15.4.10 |
| 信息显示 | JEI | 15.49.0.191 |
| 信息显示 | Jade | 11.13.3 |
| 地图 | JourneyMap | 1.20.1-6.0.2 |
| 性能 | Embeddium | 0.3.31 |
| 性能 | Oculus（着色器） | 1.8.0 |
| 性能 | FerriteCore | 6.0.1 |
| 性能 | ModernFix | 5.27.76 |
| 探索 | YUNG's Better Dungeons | 4.0.4 |
| 探索 | YUNG's Better Mineshafts | 4.0.4 |
| 探索 | YUNG's Better Strongholds | 4.0.3 |
| 探索 | Dungeons Arise | 2.1.58 |
| 探索 | Repurposed Structures | 7.1.24 |
| 建筑 | Macaw's Bridges | 3.1.2 |
| 建筑 | Macaw's Doors | 1.1.5 |
| 功能 | Quark | 4.0-462 |
| 功能 | Supplementaries | 3.1.43 |
| 通用库 | Architectury API | 9.2.14 |
| 通用库 | Cloth Config | 11.1.136 |
| 通用库 | Curios API | 5.14.1 |
| **自制 mod** | **MD Star** | **0.1.0-alpha** |

### FTB Quests 任务线（8 章 80 个任务）

| 章节 | 名称 | 任务数 | 阶段 |
| --- | --- | --- | --- |
| 1 | 欢迎来到套娃世界 | 7 | 前期 |
| 2 | 机械动力时代 | 10 | 前期 |
| 3 | 探索套娃世界 | 10 | 前期 |
| 4 | 批量生产流水线 | 8 | 中期 |
| 5 | 魔改格雷科技：蒸汽时代后 | 10 | 中期 |
| 6 | AE2 存储网络（简化配方） | 10 | 中期 |
| 7 | 现代大型机械多方块 | 13 | 后期 |
| 8 | MD 之星 - 最终毕业 | 12 | 毕业 |

### KubeJS 配方脚本（4 个）

- `01_nesting_recipes.js` - 套娃配方（非 AE2 全部复杂化）
- `02_gregtech_tweaks.js` - GT 魔改（删除基础工具，保留蒸汽时代后）
- `03_ae2_simplify.js` - AE2 配方简化
- `04_md_star.js` - MD 之星毕业链配方

---

## 3. 任务线流程图

```
欢迎 → 机械动力起家 → 探索世界
         ↓
   批量流水线
         ↓
   ┌─────┴─────┐
 格雷科技     AE2 存储
   └─────┬─────┘
         ↓
   现代大型机械多方块
         ↓
   收集 9 种精华
         ↓
   ★ 合成 MD 之星 ★（毕业）
```

---

## 4. 已知限制

1. **Twilight Forest（暮色森林）未包含**：Modrinth 无 1.20.1 Forge 版本，需从 CurseForge 手动下载
   - 下载后放入 `mods/` 目录即可
   - 影响：暮色精华任务和暮色森林探索任务可能无法完成

2. **部分 Create 附属未包含**：本版本只包含 Create 核心，未包含 25 个附属
   - 任务线中提到的 Rails/Slice/CnA 精华需要对应附属
   - 可从 CurseForge 下载附属 mod

3. **自制 mod mdstar 无贴图**：使用 Minecraft 内置贴图占位

4. **JEI/GTCEu 版本为 beta**：这是 mod 作者的正常发布方式，可放心使用

---

## 5. 游戏内操作

### 打开任务书
- 默认按键：`[ ]`（右方括号）
- 或在物品栏中点击任务书图标

### 任务系统
- 点击任务图标查看详情
- 完成 task 后点击对勾领取奖励
- 依赖任务完成后，后续任务自动解锁

### 关键指令
```
/ftbquests reload     # 重载任务（修改 .snbt 后）
/ftbquests unlock     # 解锁所有任务（管理员）
/ftbquests complete   # 完成所有任务（管理员）
```

---

## 6. 技术信息

| 项 | 值 |
| --- | --- |
| Minecraft | 1.20.1 |
| Forge | 47.3.12 |
| 整合包版本 | 0.2.0-alpha |
| mod 数量 | 28 |
| 任务数量 | 80 |
| 整合包大小 | 84 MB |
| Java 要求 | JDK 17 |

---

> "层层套娃" — 当你拆开一层配方，里面还有另一层配方在等你。
